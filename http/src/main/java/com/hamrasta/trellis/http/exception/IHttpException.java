package com.hamrasta.trellis.http.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;

public interface IHttpException {

    HttpErrorMessage getErrorMessage();

    void setPath(String path);

    HttpStatus getHttpStatus();

    Map<String, Serializable> body();

    String toString();
}
