package com.hamrasta.trellis.message.payload;

import com.hamrasta.trellis.util.string.StringUtil;
import com.hamrasta.trellis.core.payload.ResponseMessage;
import com.hamrasta.trellis.message.metadata.SendMessageStatus;
import org.apache.commons.lang3.StringUtils;

public class SendMessageResponse extends ResponseMessage {
    private SendMessageStatus status;

    private String message;

    public SendMessageStatus getStatus() {
        return status;
    }

    public void setStatus(SendMessageStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SendMessageResponse(SendMessageStatus status) {
        this(status, StringUtils.EMPTY);
    }

    public SendMessageResponse(SendMessageStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
