package com.hamrasta.trellis.message.payload;

import com.hamrasta.trellis.core.payload.IPayload;

public class EmbeddedData implements IPayload {
    private String name;

    private byte[] data;

    private String mimeType;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public EmbeddedData() {
    }

    public EmbeddedData(String name, byte[] data, String mimeType) {
        this.name = name;
        this.data = data;
        this.mimeType = mimeType;
    }
}