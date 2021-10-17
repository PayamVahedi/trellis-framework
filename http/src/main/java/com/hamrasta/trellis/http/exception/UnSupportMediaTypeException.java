package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class UnSupportMediaTypeException extends HttpException {

    public UnSupportMediaTypeException(MessageHandler message) {
        super(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public UnSupportMediaTypeException(String message) {
        super(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public UnSupportMediaTypeException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
