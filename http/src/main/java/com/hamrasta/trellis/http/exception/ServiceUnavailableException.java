package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends HttpException {

    public ServiceUnavailableException(MessageHandler message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.SERVICE_UNAVAILABLE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
