package com.hamrasta.trellis.message.payload;

import com.hamrasta.trellis.util.environment.EnvironmentUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Payload;

public class SmsConfiguration implements Payload {
    private String username;

    private String password;

    private String from;

    private String domain;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public SmsConfiguration() {
    }

    public SmsConfiguration(String username, String password, String from, String domain) {
        this.username = username;
        this.password = password;
        this.from = from;
        this.domain = domain;
    }

    public static SmsConfiguration getFromApplicationConfig() {
        String username = EnvironmentUtil.getPropertyValue("info.sms.username", StringUtils.EMPTY);
        String password = EnvironmentUtil.getPropertyValue("info.sms.password", StringUtils.EMPTY);
        String from = EnvironmentUtil.getPropertyValue("info.sms.from", StringUtils.EMPTY);
        String domain = EnvironmentUtil.getPropertyValue("info.sms.domain", "false");
        return new SmsConfiguration(username, password, from, domain);
    }
}
