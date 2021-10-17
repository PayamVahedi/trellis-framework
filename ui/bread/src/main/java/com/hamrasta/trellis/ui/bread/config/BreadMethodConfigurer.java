package com.hamrasta.trellis.ui.bread.config;

import org.springframework.web.bind.annotation.RequestMethod;

public class BreadMethodConfigurer {
    private String path;

    private RequestMethod method;

    private boolean disable = false;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public boolean isDisable() {
        return disable;
    }

    public boolean isEnable() {
        return !isDisable();
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public BreadMethodConfigurer() {
    }

    public BreadMethodConfigurer(boolean disable) {
        this.disable = disable;
    }

    public BreadMethodConfigurer(String path, RequestMethod method) {
        this.path = path;
        this.method = method;
    }
}
