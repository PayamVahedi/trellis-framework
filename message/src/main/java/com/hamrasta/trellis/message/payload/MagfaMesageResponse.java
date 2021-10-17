package com.hamrasta.trellis.message.payload;

import com.hamrasta.trellis.core.payload.ResponseMessage;

public class MagfaMesageResponse extends ResponseMessage {
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
