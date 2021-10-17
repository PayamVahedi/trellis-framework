package com.hamrasta.trellis.context.process;

public interface IProcess1<TOutput, TInput> extends IBaseProcess<TOutput> {

    TOutput execute(TInput param) throws Throwable;

}
