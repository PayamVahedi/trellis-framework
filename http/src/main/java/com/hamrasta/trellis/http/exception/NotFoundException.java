package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {

    public NotFoundException(MessageHandler message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NOT_FOUND, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
