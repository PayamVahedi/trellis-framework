package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class ToManyRequestsException extends HttpException {

    public ToManyRequestsException(MessageHandler message) {
        super(message, HttpStatus.TOO_MANY_REQUESTS);
    }

    public ToManyRequestsException(String message) {
        super(message, HttpStatus.TOO_MANY_REQUESTS);
    }

    public ToManyRequestsException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.TOO_MANY_REQUESTS, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}