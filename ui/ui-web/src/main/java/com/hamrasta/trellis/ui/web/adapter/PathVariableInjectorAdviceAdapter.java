package com.hamrasta.trellis.ui.web.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.ui.web.bind.PathVariable;
import com.hamrasta.trellis.ui.web.helper.RequestHelper;
import com.hamrasta.trellis.util.json.JsonUtil;
import com.hamrasta.trellis.util.reflection.ReflectionUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class PathVariableInjectorAdviceAdapter extends RequestBodyAdviceAdapter {
    static ObjectMapper mapper = new ObjectMapper();

    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        try {
            if (AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType)) {
                if (targetType instanceof Class) {
                    for (Field field : ((Class<?>) targetType).getDeclaredFields()) {
                        if (field.isAnnotationPresent(PathVariable.class)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            Logger.error("InjectPathVariableException", e.getMessage());
            return false;
        }
    }

    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> selectedConverterType) throws IOException {
        String bodyStr = IOUtils.toString(new InputStreamReader(inputMessage.getBody(), StandardCharsets.UTF_8));
        try {
            var instance = JsonUtil.toObject(bodyStr, mapper.getTypeFactory().constructType(targetType));
            Class<?> target = ((Class<?>) targetType);
            for (Field field : target.getDeclaredFields()) {
                if (field.isAnnotationPresent(PathVariable.class)) {
                    PathVariable property = field.getAnnotation(PathVariable.class);
                    String value = StringUtils.isBlank(property.value()) ? field.getName() : property.value();
                    ReflectionUtil.setPropertyValue(instance, field.getName(), RequestHelper.getPathVariableValue(value));
                }
            }
            return new MappingJacksonInputMessage(IOUtils.toInputStream(JsonUtil.toString(instance), StandardCharsets.UTF_8), inputMessage.getHeaders());
        } catch (Exception e) {
            Logger.error("InjectPathVariableException", e.getMessage());
            return new MappingJacksonInputMessage(IOUtils.toInputStream(bodyStr, StandardCharsets.UTF_8), inputMessage.getHeaders());
        }
    }

}