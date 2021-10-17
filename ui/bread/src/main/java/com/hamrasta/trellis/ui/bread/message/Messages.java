package com.hamrasta.trellis.ui.bread.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hamrasta.trellis.core.message.MessageHandler;

public enum Messages implements MessageHandler {
    ENTITY_NOT_FOUND,
    ID_IS_REQUIRED,
    JSON_BODY_IS_INVALID,
    NO_MESSAGE_AVAILABLE,
    MISSING_INPUT_SECRET,
    INVALID_INPUT_SECRET,
    MISSING_INPUT_RESPONSE,
    INVALID_INPUT_RESPONSE,
    INVALID_CAPTCHA_TOKEN,
    TIMEOUT_OR_DUPLICATE,
    ;

    public static Messages nameOf(String name) {
        for (Messages p : values())
            if (p.name().equalsIgnoreCase(name)) return p;
        return null;
    }

    @JsonCreator
    public static Messages forValue(String value) {
        return nameOf(value.replace('-', '_').toUpperCase());
    }

}
