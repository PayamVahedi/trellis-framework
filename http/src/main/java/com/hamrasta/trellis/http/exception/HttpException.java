package com.hamrasta.trellis.http.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamrasta.trellis.core.message.MessageHandler;
import okhttp3.Protocol;
import okhttp3.internal.http.RealResponseBody;
import okio.ByteString;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import retrofit2.Response;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class HttpException extends retrofit2.HttpException implements IHttpException {

    private final HttpErrorMessage errorMessage;

    public HttpException(MessageHandler message, HttpStatus status) {
        this(new HttpErrorMessage(Optional.ofNullable(status).orElse(HttpStatus.INTERNAL_SERVER_ERROR), message.getMessage(), message.getCode()));
    }

    public HttpException(String message, HttpStatus status) {
        this(new HttpErrorMessage(Optional.ofNullable(status).orElse(HttpStatus.INTERNAL_SERVER_ERROR), message, null));
    }

    public HttpException(HttpErrorMessage error) {
        super(
                Response.error(
                        new RealResponseBody(MediaType.APPLICATION_JSON_VALUE,
                                Optional.ofNullable(error).map(HttpErrorMessage::getMessage).orElse(StringUtils.EMPTY).length(),
                                null),
                        new okhttp3.Response.Builder()
                                .body(RealResponseBody.create(okhttp3.MediaType.get(MediaType.APPLICATION_JSON_VALUE), ByteString.EMPTY))
                                .protocol(Protocol.HTTP_1_1)
                                .request((new okhttp3.Request.Builder()).url("http://localhost/").build())
                                .code(Optional.ofNullable(error).map(HttpErrorMessage::getHttpStatus).orElse(HttpStatus.INTERNAL_SERVER_ERROR).value())
                                .message(Optional.ofNullable(error).map(HttpErrorMessage::getMessage).orElse(StringUtils.EMPTY))
                                .build()));
        this.errorMessage = error;
    }

    public void setPath(String path) {
        if (errorMessage != null)
            errorMessage.setPath(path);
    }

    public HttpErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return ObjectUtils.isNotEmpty(errorMessage) && ObjectUtils.isNotEmpty(errorMessage.getHttpStatus()) ? errorMessage.getHttpStatus() : HttpStatus.NOT_IMPLEMENTED;
    }

    public Map<String, Serializable> body() {
        return errorMessage.body();
    }

    @Override
    public String toString() {
        return toString(errorMessage);
    }

    protected String toString(Object value) {
        try {
            ObjectMapper Obj = new ObjectMapper();
            return Obj.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
