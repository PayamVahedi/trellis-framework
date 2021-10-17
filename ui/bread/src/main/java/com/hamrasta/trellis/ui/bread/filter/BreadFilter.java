package com.hamrasta.trellis.ui.bread.filter;

import com.google.common.io.CharStreams;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.metadata.EntityStateKind;
import com.hamrasta.trellis.data.core.model.ICoreEntity;
import com.hamrasta.trellis.http.exception.BadRequestException;
import com.hamrasta.trellis.http.exception.NotFoundException;
import com.hamrasta.trellis.ui.bread.annotaion.BreadMethod;
import com.hamrasta.trellis.ui.bread.annotaion.EnableBread;
import com.hamrasta.trellis.ui.bread.config.BreadConfigurer;
import com.hamrasta.trellis.ui.bread.config.BreadControllerConfigurer;
import com.hamrasta.trellis.ui.bread.config.BreadMethodConfigurer;
import com.hamrasta.trellis.ui.bread.message.Messages;
import com.hamrasta.trellis.ui.bread.metadata.Bread;
import com.hamrasta.trellis.ui.bread.metadata.HttpMethodKind;
import com.hamrasta.trellis.ui.bread.payload.Domain;
import com.hamrasta.trellis.ui.bread.payload.DomainRequest;
import com.hamrasta.trellis.util.json.JsonUtil;
import com.hamrasta.trellis.util.mapper.Exclude;
import com.hamrasta.trellis.util.mapper.ModelMapper;
import com.hamrasta.trellis.util.reflection.ReflectionUtil;
import com.hamrasta.trellis.validator.ReadOnly;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.validator.HibernateValidatorFactory;
import org.reflections.Reflections;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UriTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BreadFilter implements Filter, ModelMapper {

    private final RequestMappingHandlerMapping handler;

    private final ListableBeanFactory beanFactory;

    private Reflections reflections;

    private final Set<DomainRequest> domainRequests;

    private final Set<RequestMappingInfo> dynamicRequests;

    private final Set<RequestMappingInfo> staticRequests;

    private final Set<Domain> domains;

    private final BreadConfigurer breadConfig;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    public BreadFilter(RequestMappingHandlerMapping handler, ListableBeanFactory beanFactory) {
        this.handler = handler;
        this.beanFactory = beanFactory;
        this.breadConfig = getBreadConfig();
        if (breadConfig.isEnable()) {
            this.staticRequests = this.handler.getHandlerMethods().keySet();
            this.domains = getDomains();
            this.domainRequests = getDomainRequests();
            this.dynamicRequests = this.domainRequests.stream().map(DomainRequest::getRequest).collect(Collectors.toSet());
        } else {
            this.staticRequests = new HashSet<>();
            this.domains = new HashSet<>();
            this.domainRequests = new HashSet<>();
            this.dynamicRequests = new HashSet<>();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (noneMatch(staticRequests, req) && breadConfig.isEnable() && match(dynamicRequests, req)) {
            DomainRequest domainRequest = getDomainRequest(req);
            if (domainRequest != null) {
                if (Bread.ADD.name().equalsIgnoreCase(domainRequest.getRequest().getName())) {
                    add(req, res, domainRequest);
                    return;
                } else if (Bread.EDIT.name().equalsIgnoreCase(domainRequest.getRequest().getName())) {
                    edit(req, res, domainRequest);
                    return;
                } else if (Bread.DELETE.name().equalsIgnoreCase(domainRequest.getRequest().getName())) {
                    delete(req, res, domainRequest);
                    return;
                } else if (Bread.READ.name().equalsIgnoreCase(domainRequest.getRequest().getName())) {
                    read(req, res, domainRequest);
                    return;
                } else if (Bread.BROWSE.name().equalsIgnoreCase(domainRequest.getRequest().getName())) {
                    browse(req, res, domainRequest);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    private void add(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, DomainRequest domainRequest) {
        try {
            ICoreEntity body = getJsonBody(httpServletRequest, domainRequest);
            ICoreRepository<ICoreEntity> repository = getRepositoryByDomainRequest(domainRequest);
            Class<ICoreEntity> clazz = (Class<ICoreEntity>) Class.forName(domainRequest.getDomain().getEntity().getName());
            ICoreEntity entity = plainToClass(body, clazz, getReadOnlyField(body));
            entity = repository.save(entity);
            ICoreEntity dbEntity = repository.findById(entity.getId()).orElse(entity);
            ICoreEntity response = plainToClass(dbEntity, clazz, getExcludeField(dbEntity));
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = httpServletResponse.getWriter();
            out.print(JsonUtil.toString(response));
            out.flush();
        } catch (Exception e) {
            Logger.error("", e.getMessage());
            this.resolver.resolveException(httpServletRequest, httpServletResponse, null, e);
        }
    }

    private void edit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, DomainRequest domainRequest) {
        try {
            ICoreEntity body = getJsonBody(httpServletRequest, domainRequest);
            Class<ICoreEntity> clazz = (Class<ICoreEntity>) Class.forName(domainRequest.getDomain().getEntity().getName());
            ICoreRepository<ICoreEntity> repository = getRepositoryByDomainRequest(domainRequest);
            ICoreEntity entity = getEntityByRequestAndRepository(httpServletRequest, domainRequest, repository);
            entity = plainToClass(body, entity, getReadOnlyField(entity));
            this.checkValidation(entity);
            entity = repository.save(entity);
            ICoreEntity dbEntity = repository.findById(entity.getId()).orElse(entity);
            ICoreEntity response = plainToClass(dbEntity, clazz, getExcludeField(dbEntity));
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = httpServletResponse.getWriter();
            out.print(JsonUtil.toString(response));
            out.flush();
        } catch (Exception e) {
            Logger.error("", e.getMessage());
            this.resolver.resolveException(httpServletRequest, httpServletResponse, null, e);
        }
    }

    private void delete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, DomainRequest domainRequest) {
        try {
            ICoreRepository<ICoreEntity> repository = getRepositoryByDomainRequest(domainRequest);
            ICoreEntity entity = getEntityByRequestAndRepository(httpServletRequest, domainRequest, repository);
            repository.delete(entity);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            Logger.error("", e.getMessage());
            this.resolver.resolveException(httpServletRequest, httpServletResponse, null, e);
        }
    }

    private void read(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, DomainRequest domainRequest) {
        try {
            ICoreEntity entity = getEntityByRequest(httpServletRequest, domainRequest);
            Class<ICoreEntity> clazz = (Class<ICoreEntity>) Class.forName(domainRequest.getDomain().getEntity().getName());
            ICoreEntity response = plainToClass(entity, clazz, getExcludeField(entity));
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = httpServletResponse.getWriter();
            out.print(JsonUtil.toString(response));
            out.flush();
        } catch (Exception e) {
            Logger.error("", e.getMessage());
            this.resolver.resolveException(httpServletRequest, httpServletResponse, null, e);
        }
    }

    private void browse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, DomainRequest domainRequest) {
        try {
            Pageable pageable = getPageableFromRequest(httpServletRequest);
            ICoreRepository<ICoreEntity> repository = getRepositoryByDomainRequest(domainRequest);
            Class<ICoreEntity> clazz = (Class<ICoreEntity>) Class.forName(domainRequest.getDomain().getEntity().getName());
            Page<ICoreEntity> entities = repository.findAll(pageable);
            List<ICoreEntity> content = entities.getContent().stream().map(x -> plainToClass(x, clazz, getExcludeField(x))).collect(Collectors.toList());
            Page<ICoreEntity> response = new PageImpl<>(content, entities.getPageable(), entities.getTotalElements());
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = httpServletResponse.getWriter();
            out.print(JsonUtil.toString(response));
            out.flush();
        } catch (Exception e) {
            Logger.error("", e.getMessage());
            this.resolver.resolveException(httpServletRequest, httpServletResponse, null, e);
        }
    }

    private DomainRequest getDomainRequest(HttpServletRequest req) {
        return domainRequests.stream().filter(x -> x.getRequest().getPatternsCondition().getPatterns().stream().anyMatch(p -> new AntPathMatcher().match(p, req.getServletPath())) && x.getRequest().getMethodsCondition().getMethods().contains(RequestMethod.valueOf(req.getMethod()))).findFirst().orElse(null);
    }

    private Set<DomainRequest> getDomainRequests() {
        Set<DomainRequest> result = new HashSet<>();
        if (this.domains.isEmpty())
            return result;
        for (Domain domain : this.domains) {
            BreadControllerConfigurer config = getBreadControllerConfig(domain);
            if (config.isDisable())
                continue;
            if (config.getAdd().isEnable()) {
                RequestMappingInfo request = new RequestMappingInfo(Bread.ADD.name(), new PatternsRequestCondition(config.getAdd().getPath()), new RequestMethodsRequestCondition(config.getAdd().getMethod()), null, null, null, null, null);
                if (noneMatch(staticRequests, request))
                    result.add(new DomainRequest(domain, request));
            }
            if (config.getEdit().isEnable()) {
                RequestMappingInfo requestMappingInfo = new RequestMappingInfo(Bread.EDIT.name(), new PatternsRequestCondition(config.getEdit().getPath()), new RequestMethodsRequestCondition(config.getEdit().getMethod()), null, null, null, null, null);
                if (noneMatch(staticRequests, requestMappingInfo))
                    result.add(new DomainRequest(domain, requestMappingInfo));
            }
            if (config.getDelete().isEnable()) {
                RequestMappingInfo requestMappingInfo = new RequestMappingInfo(Bread.DELETE.name(), new PatternsRequestCondition(config.getDelete().getPath()), new RequestMethodsRequestCondition(config.getDelete().getMethod()), null, null, null, null, null);
                if (noneMatch(staticRequests, requestMappingInfo))
                    result.add(new DomainRequest(domain, requestMappingInfo));
            }
            if (config.getRead().isEnable()) {
                RequestMappingInfo requestMappingInfo = new RequestMappingInfo(Bread.READ.name(), new PatternsRequestCondition(config.getRead().getPath()), new RequestMethodsRequestCondition(config.getRead().getMethod()), null, null, null, null, null);
                if (noneMatch(staticRequests, requestMappingInfo))
                    result.add(new DomainRequest(domain, requestMappingInfo));
            }
            if (config.getBrowse().isEnable()) {
                RequestMappingInfo requestMappingInfo = new RequestMappingInfo(Bread.BROWSE.name(), new PatternsRequestCondition(config.getBrowse().getPath()), new RequestMethodsRequestCondition(config.getBrowse().getMethod()), null, null, null, null, null);
                if (noneMatch(staticRequests, requestMappingInfo))
                    result.add(new DomainRequest(domain, requestMappingInfo));
            }
        }
        return result;

    }

    private Set<Domain> getDomains() {
        Collection<String> names = CollectionUtils.intersection(beanFactory.getBeansWithAnnotation(EnableBread.class).keySet(), beanFactory.getBeansOfType(ICoreRepository.class).keySet());
        Set<Domain> result = new HashSet<>();
        if (names.isEmpty())
            return result;
        for (String name : names) {
            Class<? extends ICoreRepository<? extends ICoreEntity>> repository = getRepositoryClass(name);
            Class<? extends ICoreEntity> entity = getEntityClass(repository);
            if (entity != null)
                result.add(new Domain(entity, repository));
        }
        return result;
    }

    private String getEntityName(String repositoryName) {
        try {
            return StringUtils.replaceIgnoreCase(repositoryName, "Repository", "").substring(1);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    private <TEntity extends ICoreEntity> Class<TEntity> getEntityClass(Class<? extends ICoreRepository<? extends ICoreEntity>> repository) {
        if (repository == null)
            return null;
        String entityName = getEntityName(repository.getSimpleName());
        Set<Class<? extends ICoreEntity>> entities = getReflection().getSubTypesOf(ICoreEntity.class);
        if (entities == null || entities.isEmpty())
            return null;
        return (Class<TEntity>) entities.stream().filter(x -> x.getSimpleName().equalsIgnoreCase(entityName)).findFirst().orElse(null);
    }

    private <TEntity extends ICoreEntity> TEntity getEntityByRequestAndRepository(HttpServletRequest httpServletRequest, DomainRequest domainRequest, ICoreRepository<TEntity> repository) throws NotFoundException, BadRequestException {
        String id = getIdFromPathVariable(httpServletRequest, domainRequest);
        return getEntityById(id, repository, domainRequest.getDomain().getEntity());
    }

    private <TEntity extends ICoreEntity> TEntity getEntityByRequest(HttpServletRequest httpServletRequest, DomainRequest domainRequest) throws NotFoundException, BadRequestException {
        String id = getIdFromPathVariable(httpServletRequest, domainRequest);
        ICoreRepository<TEntity> repository = getRepositoryByDomainRequest(domainRequest);
        return getEntityById(id, repository, domainRequest.getDomain().getEntity());
    }

    private <TEntity extends ICoreEntity, TRepository extends ICoreRepository<TEntity>> Class<TRepository> getRepositoryClass(String repositoryName) {
        Set<Class<? extends ICoreRepository>> repositories = getReflection().getSubTypesOf(ICoreRepository.class);
        if (repositories == null || repositories.isEmpty())
            return null;
        return (Class<TRepository>) repositories.stream().filter(x -> x.getSimpleName().equalsIgnoreCase(repositoryName)).findFirst().orElse(null);
    }

    private <TEntity extends ICoreEntity, TRepository extends ICoreRepository<TEntity>> TRepository getRepositoryByDomainRequest(DomainRequest domainRequest) throws NotFoundException {
        return (TRepository) findRepositoryByDomainRequest(domainRequest).orElseThrow(() -> new NotFoundException(domainRequest.getDomain().getEntity().getSimpleName().toUpperCase() + "_REPOSITORY_NOT_FOUND"));
    }

    private <TEntity extends ICoreEntity, TRepository extends ICoreRepository<TEntity>> Optional<TRepository> findRepositoryByDomainRequest(DomainRequest domainRequest) {
        return domainRequest == null || domainRequest.getDomain() == null || domainRequest.getDomain().getRepository() == null || StringUtils.isBlank(domainRequest.getDomain().getRepository().getSimpleName()) ?
                Optional.empty() :
                findRepositoryByName(domainRequest.getDomain().getRepository().getSimpleName());
    }

    private <TEntity extends ICoreEntity, TRepository extends ICoreRepository<TEntity>> Optional<TRepository> findRepositoryByName(String name) {
        return beanFactory.containsBean(name) ? Optional.of((TRepository) beanFactory.getBean(name)) : Optional.empty();
    }

    private <TEntity extends ICoreEntity> TEntity getEntityById(String id, ICoreRepository<TEntity> repository, Class<? extends ICoreEntity> entity) throws NotFoundException {
        return findEntityById(id, repository).orElseThrow(() -> new NotFoundException(entity.getSimpleName().toUpperCase() + "_NOT_FOUND"));
    }

    private <TEntity extends ICoreEntity> Optional<TEntity> findEntityById(String id, ICoreRepository<TEntity> repository) {
        return StringUtils.isBlank(id) ? Optional.empty() : repository.findById(id);
    }

    private <TEntity extends ICoreEntity> TEntity getJsonBody(HttpServletRequest httpServletRequest, DomainRequest domainRequest) throws BadRequestException {
        return (TEntity) findJsonBody(httpServletRequest, domainRequest).orElseThrow(() -> new BadRequestException(Messages.JSON_BODY_IS_INVALID));
    }

    private <TEntity extends ICoreEntity> Optional<TEntity> findJsonBody(HttpServletRequest httpServletRequest, DomainRequest domainRequest) {
        try {
            String body = CharStreams.toString(httpServletRequest.getReader());
            if (StringUtils.isBlank(body))
                return Optional.empty();
            return Optional.of((TEntity) JsonUtil.toObject(body, Class.forName(domainRequest.getDomain().getEntity().getName())));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Pageable getPageableFromRequest(HttpServletRequest httpServletRequest) {
        String pageParameter = httpServletRequest.getParameter("page");
        String sizeParameter = httpServletRequest.getParameter("size");
        int page = StringUtils.isBlank(pageParameter) || !NumberUtils.isDigits(pageParameter) ? 0 : Integer.parseInt(pageParameter);
        int size = StringUtils.isBlank(sizeParameter) || !NumberUtils.isDigits(sizeParameter) ? 20 : Integer.parseInt(sizeParameter);
        return PageRequest.of(page, size, Sort.Direction.DESC, "created");
    }

    private String getIdFromPathVariable(HttpServletRequest httpServletRequest, DomainRequest domainRequest) throws BadRequestException {
        String id = findIdFromPathVariable(httpServletRequest, domainRequest);
        if (StringUtils.isBlank(id))
            throw new BadRequestException(Messages.ID_IS_REQUIRED);
        return id;
    }

    private String findIdFromPathVariable(HttpServletRequest httpServletRequest, DomainRequest domainRequest) {
        String uri = getMatchPattern(httpServletRequest, domainRequest);
        if (StringUtils.isBlank(uri))
            return StringUtils.EMPTY;
        UriTemplate template = new UriTemplate(uri);
        Map<String, String> parameters = template.match(httpServletRequest.getServletPath());
        if (parameters.isEmpty())
            return StringUtils.EMPTY;
        return StringUtils.isBlank(parameters.get("id")) ? parameters.entrySet().iterator().next().getValue() : parameters.get("id");
    }

    private boolean noneMatch(Set<RequestMappingInfo> requests, HttpServletRequest req) {
        return !match(requests, req);
    }

    private boolean match(Set<RequestMappingInfo> requests, HttpServletRequest req) {
        return requests.stream().anyMatch(x -> x.getPatternsCondition().getPatterns().stream().anyMatch(p -> new AntPathMatcher().match(p, req.getServletPath())) && x.getMethodsCondition().getMethods().contains(RequestMethod.valueOf(req.getMethod())));
    }

    private boolean noneMatch(Set<RequestMappingInfo> requests, RequestMappingInfo request) {
        return !match(requests, request);
    }

    private boolean match(Set<RequestMappingInfo> requests, RequestMappingInfo request) {
        return requests.stream().anyMatch(x -> x.getPatternsCondition().getPatterns().stream().anyMatch(p -> request.getPatternsCondition().getPatterns().contains(p)) &&
                x.getMethodsCondition().getMethods().stream().anyMatch(m -> request.getMethodsCondition().getMethods().contains(m)));
    }

    private String getMatchPattern(HttpServletRequest httpServletRequest, DomainRequest domainRequest) {
        return domainRequest.getRequest().getPatternsCondition().getPatterns().stream().filter(x -> new AntPathMatcher().match(x, httpServletRequest.getServletPath())).findFirst().orElse(StringUtils.EMPTY);
    }

    private void checkValidation(ICoreEntity entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = (factory.unwrap(HibernateValidatorFactory.class)).usingContext().constraintValidatorPayload(EntityStateKind.UPDATE).getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(entity, new Class[0]);
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    private Reflections getReflection() {
        if (this.reflections == null)
            this.reflections = new Reflections(getBasePackageName());
        return this.reflections;
    }

    private Set<String> getBasePackageName() {
        return breadConfig.getScanBasePackages();
    }

    private BreadConfigurer getBreadConfig() {
        Collection<?> applicationConfigurations = beanFactory.getBeansWithAnnotation(SpringBootApplication.class).values();
        Object applicationConfiguration = applicationConfigurations.stream().findFirst().orElse(null);
        SpringBootApplication annotation = applicationConfiguration == null || !applicationConfiguration.getClass().isAnnotationPresent(SpringBootApplication.class) ? null : applicationConfiguration.getClass().getAnnotation(SpringBootApplication.class);
        if (annotation == null)
            return new BreadConfigurer(true);
        Set<String> scanPackages = new HashSet<>();
        if (annotation.scanBasePackages().length <= 0 && annotation.scanBasePackageClasses().length <= 0) {
            scanPackages.add(applicationConfiguration.getClass().getPackageName());
        } else {
            if (annotation.scanBasePackages().length > 0)
                scanPackages.addAll(Arrays.asList(annotation.scanBasePackages()));
            else {
                scanPackages.addAll(Arrays.stream(annotation.scanBasePackageClasses()).map(Class::getName).collect(Collectors.toSet()));
            }
        }
        return new BreadConfigurer(scanPackages);
    }

    private BreadControllerConfigurer getBreadControllerConfig(Domain domain) {
        EnableBread config = !domain.getRepository().isAnnotationPresent(EnableBread.class) ? null : domain.getRepository().getAnnotation(EnableBread.class);
        if (config == null)
            return new BreadControllerConfigurer(true);
        Set<BreadMethod> methods = Set.of(config.methods());
        BreadMethod addMethod = methods.isEmpty() ? null : methods.stream().filter(x -> Bread.ADD.equals(x.kind())).findFirst().orElse(null);
        BreadMethod editMethod = methods.isEmpty() ? null : methods.stream().filter(x -> Bread.EDIT.equals(x.kind())).findFirst().orElse(null);
        BreadMethod deleteMethod = methods.isEmpty() ? null : methods.stream().filter(x -> Bread.DELETE.equals(x.kind())).findFirst().orElse(null);
        BreadMethod readMethod = methods.isEmpty() ? null : methods.stream().filter(x -> Bread.READ.equals(x.kind())).findFirst().orElse(null);
        BreadMethod browseMethod = methods.isEmpty() ? null : methods.stream().filter(x -> Bread.BROWSE.equals(x.kind())).findFirst().orElse(null);
        String path = StringUtils.isBlank(config.path()) ? domain.getEntity().getSimpleName().toLowerCase() + "s" : config.path();
        BreadMethodConfigurer add;
        BreadMethodConfigurer edit;
        BreadMethodConfigurer delete;
        BreadMethodConfigurer read;
        BreadMethodConfigurer browse;

        if (addMethod == null) {
            add = new BreadMethodConfigurer(path, RequestMethod.POST);
        } else if (addMethod.disable()) {
            add = new BreadMethodConfigurer(true);
        } else {
            String methodPath = StringUtils.isBlank(addMethod.path()) ? path : addMethod.path();
            RequestMethod method = HttpMethodKind.DEFAULT.equals(addMethod.method()) ? RequestMethod.POST : addMethod.method().getRequest();
            add = new BreadMethodConfigurer(methodPath, method);
        }

        if (editMethod == null) {
            edit = new BreadMethodConfigurer(path + "/{id}", RequestMethod.PUT);
        } else if (editMethod.disable()) {
            edit = new BreadMethodConfigurer(true);
        } else {
            String methodPath = StringUtils.isBlank(editMethod.path()) ? path + "/{id}" : editMethod.path();
            RequestMethod method = HttpMethodKind.DEFAULT.equals(editMethod.method()) ? RequestMethod.PUT : editMethod.method().getRequest();
            edit = new BreadMethodConfigurer(methodPath, method);
        }

        if (deleteMethod == null) {
            delete = new BreadMethodConfigurer(path + "/{id}", RequestMethod.DELETE);
        } else if (deleteMethod.disable()) {
            delete = new BreadMethodConfigurer(true);
        } else {
            String methodPath = StringUtils.isBlank(deleteMethod.path()) ? path + "/{id}" : deleteMethod.path();
            RequestMethod method = HttpMethodKind.DEFAULT.equals(deleteMethod.method()) ? RequestMethod.DELETE : deleteMethod.method().getRequest();
            delete = new BreadMethodConfigurer(methodPath, method);
        }

        if (readMethod == null) {
            read = new BreadMethodConfigurer(path + "/{id}", RequestMethod.GET);
        } else if (readMethod.disable()) {
            read = new BreadMethodConfigurer(true);
        } else {
            String methodPath = StringUtils.isBlank(readMethod.path()) ? path + "/{id}" : readMethod.path();
            RequestMethod method = HttpMethodKind.DEFAULT.equals(readMethod.method()) ? RequestMethod.GET : readMethod.method().getRequest();
            read = new BreadMethodConfigurer(methodPath, method);
        }

        if (browseMethod == null) {
            browse = new BreadMethodConfigurer(path, RequestMethod.GET);
        } else if (browseMethod.disable()) {
            browse = new BreadMethodConfigurer(true);
        } else {
            String methodPath = StringUtils.isBlank(browseMethod.path()) ? path : browseMethod.path();
            RequestMethod method = HttpMethodKind.DEFAULT.equals(browseMethod.method()) ? RequestMethod.GET : browseMethod.method().getRequest();
            browse = new BreadMethodConfigurer(methodPath, method);
        }
        return new BreadControllerConfigurer(path, add, edit, delete, read, browse);
    }

    private <TEntity extends ICoreEntity> Set<Field> getReadOnlyField(TEntity entity) {
        Set<Field> fields = ReflectionUtil.getFields(entity.getClass());
        Set<Field> readOnlyFields = new HashSet<>();
        if (fields.isEmpty())
            return readOnlyFields;
        for (Field field : fields) {
            if (field.isAnnotationPresent(ReadOnly.class))
                readOnlyFields.add(field);
        }
        return readOnlyFields;
    }

    private <TEntity extends ICoreEntity> Set<Field> getExcludeField(TEntity entity) {
        Set<Field> fields = ReflectionUtil.getFields(entity.getClass());
        Set<Field> excludeFields = new HashSet<>();
        if (fields.isEmpty())
            return excludeFields;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Exclude.class))
                excludeFields.add(field);
        }
        return excludeFields;
    }
}