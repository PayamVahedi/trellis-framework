package com.hamrasta.trellis.context.process;

public interface IProcess8<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7, TInput8> extends IBaseProcess<TOutput> {

    TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, TInput8 t8) throws Throwable;

}
