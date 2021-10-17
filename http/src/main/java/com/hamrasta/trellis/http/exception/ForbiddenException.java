package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class ForbiddenException  extends HttpException {

    public ForbiddenException(MessageHandler message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.FORBIDDEN, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}