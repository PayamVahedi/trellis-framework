package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class ProcessingException  extends HttpException {

    public ProcessingException(MessageHandler message) {
        super(message, HttpStatus.PROCESSING);
    }

    public ProcessingException(String message) {
        super(message, HttpStatus.PROCESSING);
    }

    public ProcessingException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PROCESSING, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}