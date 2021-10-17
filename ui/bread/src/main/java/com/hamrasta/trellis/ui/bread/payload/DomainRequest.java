package com.hamrasta.trellis.ui.bread.payload;

import com.hamrasta.trellis.core.payload.ResponseMessage;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public class DomainRequest extends ResponseMessage {
    private Domain domain;

    private RequestMappingInfo request;

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public RequestMappingInfo getRequest() {
        return request;
    }

    public void setRequest(RequestMappingInfo request) {
        this.request = request;
    }

    public DomainRequest() {
    }

    public DomainRequest(Domain domain, RequestMappingInfo request) {
        this.domain = domain;
        this.request = request;
    }

    @Override
    public String toString() {
        return "DomainRequest{" +
                "domain=" + domain +
                ", request=" + request +
                '}';
    }
}
