package com.hamrasta.trellis.context.process;

public interface IProcess<TOutput> extends IBaseProcess<TOutput> {

    TOutput execute() throws Throwable;

}
