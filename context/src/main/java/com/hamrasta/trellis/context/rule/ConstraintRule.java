package com.hamrasta.trellis.context.rule;

import com.hamrasta.trellis.context.provider.ActionContextProvider;
import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.http.exception.HttpException;
import com.hamrasta.trellis.core.payload.IPayload;
import com.hamrasta.trellis.context.action.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public abstract class ConstraintRule<T> extends Rule<T> implements ActionContextProvider {

    public abstract String message(T t);

    public HttpStatus httpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public boolean isEnable() {
        return true;
    }

}
