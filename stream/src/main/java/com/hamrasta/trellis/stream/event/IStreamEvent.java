package com.hamrasta.trellis.stream.event;

import com.hamrasta.trellis.context.provider.ActionContextProvider;
import com.hamrasta.trellis.core.log.Logger;

import java.util.function.Function;

public interface IStreamEvent<TOutput, TInput> extends Function<TInput, TOutput>, ActionContextProvider {

    TOutput execute(TInput request) throws Throwable;

    @Override
    default TOutput apply(TInput t) {
        try {
            return execute(t);
        } catch (Throwable throwable) {
            Logger.error("", throwable.getMessage());
            return null;
        }
    }
}
