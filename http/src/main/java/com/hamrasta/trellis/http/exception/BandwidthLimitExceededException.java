package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class BandwidthLimitExceededException extends HttpException {

    public BandwidthLimitExceededException(MessageHandler message) {
        super(message, HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    }

    public BandwidthLimitExceededException(String message) {
        super(message, HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    }

    public BandwidthLimitExceededException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
