package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class NotExtendedException extends HttpException {

    public NotExtendedException(MessageHandler message) {
        super(message, HttpStatus.NOT_EXTENDED);
    }

    public NotExtendedException(String message) {
        super(message, HttpStatus.NOT_EXTENDED);
    }

    public NotExtendedException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NOT_EXTENDED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
