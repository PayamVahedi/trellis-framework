package com.hamrasta.trellis.ui.bread.config;

import java.util.Set;

public class BreadConfigurer {
    private boolean disable;

    private Set<String> scanBasePackages;

    public boolean isDisable() {
        return disable;
    }

    public boolean isEnable() {
        return !isDisable();
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public Set<String> getScanBasePackages() {
        return scanBasePackages;
    }

    public void setScanBasePackages(Set<String> scanBasePackages) {
        this.scanBasePackages = scanBasePackages;
    }

    public BreadConfigurer() {
    }

    public BreadConfigurer(boolean disable) {
        this.disable = disable;
    }

    public BreadConfigurer(Set<String> scanBasePackages) {
        this.disable = false;
        this.scanBasePackages = scanBasePackages;
    }
}
