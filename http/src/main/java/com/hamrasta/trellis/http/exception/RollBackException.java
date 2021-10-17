package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class RollBackException extends HttpException {

    public RollBackException(MessageHandler message) {
        super(message, HttpStatus.CONFLICT);
    }

    public RollBackException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public RollBackException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.CONFLICT, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
