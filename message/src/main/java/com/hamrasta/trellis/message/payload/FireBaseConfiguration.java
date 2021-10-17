package com.hamrasta.trellis.message.payload;

import com.hamrasta.trellis.util.environment.EnvironmentUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Payload;

public class FireBaseConfiguration implements Payload {
    private String credential;

    private String name;

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FireBaseConfiguration() {
    }

    public FireBaseConfiguration(String credential) {
        this.credential = credential;
    }

    public FireBaseConfiguration(String credential, String name) {
        this.credential = credential;
        this.name = name;
    }

    public static FireBaseConfiguration getFromApplicationConfig() {
        String credential = EnvironmentUtil.getPropertyValue("info.fcm.credential", StringUtils.EMPTY);
        String name = EnvironmentUtil.getPropertyValue("info.fcm.name", "[DEFAULT]");
        return new FireBaseConfiguration(credential, name);
    }

    @Override
    public String toString() {
        return "FireBaseConfiguration{" +
                "credential='" + credential + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
