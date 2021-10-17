package com.hamrasta.trellis.context.process;

public interface IProcess4<TOutput, TInput1, TInput2, TInput3, TInput4> extends IBaseProcess<TOutput> {

    TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4) throws Throwable;

}
