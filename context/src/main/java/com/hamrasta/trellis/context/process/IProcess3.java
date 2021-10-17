package com.hamrasta.trellis.context.process;

public interface IProcess3<TOutput, TInput1, TInput2, TInput3> extends IBaseProcess<TOutput> {

    TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3) throws Throwable;

}
