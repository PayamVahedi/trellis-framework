package com.hamrasta.trellis.context.process;

public interface IProcess5<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> extends IBaseProcess<TOutput> {

    TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5) throws Throwable;

}
