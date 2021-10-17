package com.hamrasta.trellis.oauth.resource.payload;

import java.util.Date;

public class Token {
    private String id;

    private String accessToken;

    private String scope;

    private Date expireDate;

    private boolean isExpired;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Token() {
    }

    public Token(String id, String accessToken, String scope, Date expireDate, boolean isExpired) {
        this.id = id;
        this.accessToken = accessToken;
        this.scope = scope;
        this.expireDate = expireDate;
        this.isExpired = isExpired;
    }
}
