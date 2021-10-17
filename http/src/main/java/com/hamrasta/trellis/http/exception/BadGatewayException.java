package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class BadGatewayException extends HttpException {

    public BadGatewayException(MessageHandler message) {
        super(message, HttpStatus.BAD_GATEWAY);
    }

    public BadGatewayException(String message) {
        super(message, HttpStatus.BAD_GATEWAY);
    }

    public BadGatewayException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.BAD_GATEWAY, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
