package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException {

    public BadRequestException(MessageHandler message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.BAD_REQUEST, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
