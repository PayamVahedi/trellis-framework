package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class PreConditionRequiredException extends HttpException {

    public PreConditionRequiredException(MessageHandler message) {
        super(message, HttpStatus.PRECONDITION_REQUIRED);
    }

    public PreConditionRequiredException(String message) {
        super(message, HttpStatus.PRECONDITION_REQUIRED);
    }

    public PreConditionRequiredException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PRECONDITION_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
