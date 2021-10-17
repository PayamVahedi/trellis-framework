package com.hamrasta.trellis.context.process;


public interface IProcess2<TOutput, TInput1, TInput2> extends IBaseProcess<TOutput> {

    TOutput execute(TInput1 t1, TInput2 t2) throws Throwable;

}
