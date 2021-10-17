package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class ProxyAuthenticationRequired extends HttpException {

    public ProxyAuthenticationRequired(MessageHandler message) {
        super(message, HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    public ProxyAuthenticationRequired(String message) {
        super(message, HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    public ProxyAuthenticationRequired(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}