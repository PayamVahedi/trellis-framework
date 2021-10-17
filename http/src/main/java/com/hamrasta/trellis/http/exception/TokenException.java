package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class TokenException extends HttpException {

    public TokenException(MessageHandler message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public TokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public TokenException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.UNAUTHORIZED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
