package com.hamrasta.trellis.message.payload;

import com.hamrasta.trellis.util.environment.EnvironmentUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Payload;

public class MailConfiguration implements Payload {
    private String host;

    private String port;

    private String username;

    private String password;

    private String from;

    private String enableAuthentication;

    private String enableStartTLS;

    private String enableSSL;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

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

    public String getEnableAuthentication() {
        return enableAuthentication;
    }

    public void setEnableAuthentication(String enableAuthentication) {
        this.enableAuthentication = enableAuthentication;
    }

    public String getEnableStartTLS() {
        return enableStartTLS;
    }

    public void setEnableStartTLS(String enableStartTLS) {
        this.enableStartTLS = enableStartTLS;
    }

    public String getEnableSSL() {
        return enableSSL;
    }

    public void setEnableSSL(String enableSSL) {
        this.enableSSL = enableSSL;
    }

    public MailConfiguration() {
    }

    public MailConfiguration(String host, String port, String username, String password, String from, String enableAuthentication, String enableStartTLS, String enableSSL) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.from = from;
        this.enableAuthentication = enableAuthentication;
        this.enableStartTLS = enableStartTLS;
        this.enableSSL = enableSSL;
    }

    public static MailConfiguration getFromApplicationConfig() {
        String host = EnvironmentUtil.getPropertyValue("spring.mail.host", StringUtils.EMPTY);
        String port = EnvironmentUtil.getPropertyValue("spring.mail.port", StringUtils.EMPTY);
        String username = EnvironmentUtil.getPropertyValue("spring.mail.username", StringUtils.EMPTY);
        String password = EnvironmentUtil.getPropertyValue("spring.mail.password", StringUtils.EMPTY);
        String from = EnvironmentUtil.getPropertyValue("spring.mail.properties.from", StringUtils.EMPTY);
        String enableAuthentication = EnvironmentUtil.getPropertyValue("spring.mail.properties.enable-authentication", "false");
        String enableStartTLS = EnvironmentUtil.getPropertyValue("spring.mail.properties.enable-start-tls", "false");
        String enableSSL = EnvironmentUtil.getPropertyValue("spring.mail.properties.enable-ssl", "false");
        return new MailConfiguration(host, port, username, password, from, enableAuthentication, enableStartTLS, enableSSL);
    }

    @Override
    public String toString() {
        return "MailConfiguration{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enableAuthentication=" + enableAuthentication +
                ", enableStartTLS=" + enableStartTLS +
                ", enableSSL=" + enableSSL +
                '}';
    }
}
