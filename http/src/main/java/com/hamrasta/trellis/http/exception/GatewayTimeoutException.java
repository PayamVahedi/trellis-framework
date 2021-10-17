package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class GatewayTimeoutException extends HttpException {

    public GatewayTimeoutException(MessageHandler message) {
        super(message, HttpStatus.GATEWAY_TIMEOUT);
    }

    public GatewayTimeoutException(String message) {
        super(message, HttpStatus.GATEWAY_TIMEOUT);
    }

    public GatewayTimeoutException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.GATEWAY_TIMEOUT, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
