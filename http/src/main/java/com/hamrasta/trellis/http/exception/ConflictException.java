package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class ConflictException extends HttpException {

    public ConflictException(MessageHandler message) {
        super(message, HttpStatus.CONFLICT);
    }

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public ConflictException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.CONFLICT, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
