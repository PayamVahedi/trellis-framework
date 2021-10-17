package com.hamrasta.trellis.http.exception;

import com.hamrasta.trellis.core.payload.IPayload;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class ErrorMessage implements IPayload {
    private Date timestamp;

    private Integer status;

    private String message;

    private String path;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this(message, null, StringUtils.EMPTY);
    }

    public ErrorMessage(String message, Integer status) {
        this(message, status, StringUtils.EMPTY);
    }

    public ErrorMessage(String message, Integer status, String path) {
        this(message, status, path, new Date());
    }

    public ErrorMessage(String message, Integer status, String path, Date timestamp) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.path = path;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
