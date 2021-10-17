package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class PayloadTooLargeException extends HttpException {

    public PayloadTooLargeException(MessageHandler message) {
        super(message, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    public PayloadTooLargeException(String message) {
        super(message, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    public PayloadTooLargeException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PAYLOAD_TOO_LARGE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
