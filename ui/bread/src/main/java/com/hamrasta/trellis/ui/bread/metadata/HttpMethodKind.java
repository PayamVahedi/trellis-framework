package com.hamrasta.trellis.ui.bread.metadata;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

public enum HttpMethodKind {
    GET(RequestMethod.GET, HttpMethod.GET),
    POST(RequestMethod.POST, HttpMethod.POST),
    PUT(RequestMethod.PUT, HttpMethod.PUT),
    PATCH(RequestMethod.PATCH, HttpMethod.PATCH),
    DELETE(RequestMethod.DELETE, HttpMethod.DELETE),
    DEFAULT(null, null);

    private final RequestMethod request;

    private final HttpMethod method;

    public RequestMethod getRequest() {
        return request;
    }

    public HttpMethod getMethod() {
        return method;
    }

    HttpMethodKind(RequestMethod request, HttpMethod method) {
        this.request = request;
        this.method = method;
    }
}
