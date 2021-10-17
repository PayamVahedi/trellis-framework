package com.hamrasta.trellis.ui.web.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamrasta.trellis.ui.web.message.Messages;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class GoogleCaptchaVerifyResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challenge_ts;

    @JsonProperty("action")
    private String action;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private Set<Messages> errorCodes;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getChallenge_ts() {
        return challenge_ts;
    }

    public void setChallenge_ts(String challenge_ts) {
        this.challenge_ts = challenge_ts;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Set<Messages> getErrorCodes() {
        return errorCodes == null ? new HashSet<>() : errorCodes;
    }

    public void setErrorCodes(Set<Messages> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public Messages getErrorCode() {
        return isSuccess() ? null : getErrorCodes().stream().findFirst().orElse(Messages.INVALID_CAPTCHA_TOKEN);
    }

}
