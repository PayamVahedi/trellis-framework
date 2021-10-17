package com.hamrasta.trellis.context.provider;

import com.hamrasta.trellis.context.action.*;
import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.http.exception.HttpException;

public interface ActionContextProvider {
    default <TAction extends Action<TOutput>, TOutput>
    TOutput call(Class<TAction> action) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute();
    }

    default <TAction extends Action1<TOutput, TInput1>,
            TOutput,
            TInput1>
    TOutput call(Class<TAction> action, TInput1 t1) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1);
    }

    default <TAction extends Action2<TOutput, TInput1, TInput2>,
            TOutput,
            TInput1,
            TInput2,
            TException extends HttpException>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2);
    }

    default <TAction extends Action3<TOutput, TInput1, TInput2, TInput3>,
            TOutput,
            TInput1,
            TInput2,
            TInput3>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3);
    }

    default <TAction extends Action4<TOutput, TInput1, TInput2, TInput3, TInput4>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4);
    }

    default <TAction extends Action5<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4,
            TInput5>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5);
    }

    default <TAction extends Action6<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4,
            TInput5,
            TInput6>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6);
    }

    default <TAction extends Action7<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4,
            TInput5,
            TInput6,
            TInput7>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6, t7);
    }

    default <TAction extends Action8<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7, TInput8>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4,
            TInput5,
            TInput6,
            TInput7,
            TInput8>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, TInput8 t8) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    default <TAction extends Action9<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7, TInput8, TInput9>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4,
            TInput5,
            TInput6,
            TInput7,
            TInput8,
            TInput9>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, TInput8 t8, TInput9 t9) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default <TAction extends Action10<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7, TInput8, TInput9, TInput10>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4,
            TInput5,
            TInput6,
            TInput7,
            TInput8,
            TInput9,
            TInput10>
    TOutput call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, TInput8 t8, TInput9 t9, TInput10 t10) throws Throwable {
        return ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }
}
