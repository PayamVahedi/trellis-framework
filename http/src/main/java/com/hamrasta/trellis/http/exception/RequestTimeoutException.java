package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class RequestTimeoutException extends HttpException {

    public RequestTimeoutException(MessageHandler message) {
        super(message, HttpStatus.REQUEST_TIMEOUT);
    }

    public RequestTimeoutException(String message) {
        super(message, HttpStatus.REQUEST_TIMEOUT);
    }

    public RequestTimeoutException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.REQUEST_TIMEOUT, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
