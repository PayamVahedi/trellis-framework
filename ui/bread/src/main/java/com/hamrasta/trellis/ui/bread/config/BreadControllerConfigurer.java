package com.hamrasta.trellis.ui.bread.config;

public class BreadControllerConfigurer {
    private String path;

    private boolean disable;

    private BreadMethodConfigurer add;

    private BreadMethodConfigurer edit;

    private BreadMethodConfigurer delete;

    private BreadMethodConfigurer read;

    private BreadMethodConfigurer browse;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public BreadMethodConfigurer getAdd() {
        return add;
    }

    public void setAdd(BreadMethodConfigurer add) {
        this.add = add;
    }

    public BreadMethodConfigurer getEdit() {
        return edit;
    }

    public void setEdit(BreadMethodConfigurer edit) {
        this.edit = edit;
    }

    public BreadMethodConfigurer getDelete() {
        return delete;
    }

    public void setDelete(BreadMethodConfigurer delete) {
        this.delete = delete;
    }

    public BreadMethodConfigurer getRead() {
        return read;
    }

    public void setRead(BreadMethodConfigurer read) {
        this.read = read;
    }

    public BreadMethodConfigurer getBrowse() {
        return browse;
    }

    public void setBrowse(BreadMethodConfigurer browse) {
        this.browse = browse;
    }

    public BreadControllerConfigurer() {
    }

    public BreadControllerConfigurer(boolean disable) {
        this.disable = disable;
    }

    public BreadControllerConfigurer(String path, BreadMethodConfigurer add, BreadMethodConfigurer edit, BreadMethodConfigurer delete, BreadMethodConfigurer read, BreadMethodConfigurer browse) {
        this.path = path;
        this.add = add;
        this.edit = edit;
        this.delete = delete;
        this.read = read;
        this.browse = browse;
        this.disable = false;
    }
}
