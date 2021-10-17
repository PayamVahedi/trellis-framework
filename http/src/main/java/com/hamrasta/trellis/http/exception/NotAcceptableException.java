package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class NotAcceptableException extends HttpException {

    public NotAcceptableException(MessageHandler message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }

    public NotAcceptableException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }

    public NotAcceptableException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NOT_ACCEPTABLE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
