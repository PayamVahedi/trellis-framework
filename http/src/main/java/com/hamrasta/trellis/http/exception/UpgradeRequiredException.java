package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.message.MessageHandler;
import org.springframework.http.HttpStatus;

public class UpgradeRequiredException extends HttpException {

    public UpgradeRequiredException(MessageHandler message) {
        super(message, HttpStatus.UPGRADE_REQUIRED);
    }

    public UpgradeRequiredException(String message) {
        super(message, HttpStatus.UPGRADE_REQUIRED);
    }

    public UpgradeRequiredException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.UPGRADE_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}