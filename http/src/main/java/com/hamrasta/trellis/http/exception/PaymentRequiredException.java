package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class PaymentRequiredException extends HttpException {

    public PaymentRequiredException(MessageHandler message) {
        super(message, HttpStatus.PAYMENT_REQUIRED);
    }

    public PaymentRequiredException(String message) {
        super(message, HttpStatus.PAYMENT_REQUIRED);
    }

    public PaymentRequiredException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PAYMENT_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
