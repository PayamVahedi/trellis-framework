package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class NotImplementedException extends HttpException {

    public NotImplementedException(MessageHandler message) {
        super(message, HttpStatus.NOT_IMPLEMENTED);
    }

    public NotImplementedException(String message) {
        super(message, HttpStatus.NOT_IMPLEMENTED);
    }

    public NotImplementedException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NOT_IMPLEMENTED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
