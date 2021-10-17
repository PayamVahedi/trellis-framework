package com.hamrasta.trellis.context.provider;

import com.hamrasta.trellis.context.process.*;
import com.hamrasta.trellis.core.application.ApplicationContextProvider;

public interface ProcessContextProvider {
    default <TProcess extends IProcess<TProcessOutput>,
            TProcessOutput>
    TProcessOutput call(Class<TProcess> process) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute();
    }

    default <TProcess extends IProcess1<TProcessOutput, TProcessInput>,
            TProcessOutput,
            TProcessInput>
    TProcessOutput call(Class<TProcess> process, TProcessInput t1) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1);
    }

    default <TProcess extends IProcess2<TProcessOutput, TProcessInput1, TProcessInput2>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2);
    }

    default <TProcess extends IProcess3<TProcessOutput, TProcessInput1, TProcessInput2, TProcessInput3>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2,
            TProcessInput3>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2, TProcessInput3 t3) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2, t3);
    }

    default <TProcess extends IProcess4<TProcessOutput, TProcessInput1, TProcessInput2, TProcessInput3, TProcessInput4>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2,
            TProcessInput3,
            TProcessInput4>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2, TProcessInput3 t3, TProcessInput4 t4) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2, t3, t4);
    }

    default <TProcess extends IProcess5<TProcessOutput, TProcessInput1, TProcessInput2, TProcessInput3, TProcessInput4, TProcessInput5>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2,
            TProcessInput3,
            TProcessInput4,
            TProcessInput5>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2, TProcessInput3 t3, TProcessInput4 t4, TProcessInput5 t5) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2, t3, t4, t5);
    }

    default <TProcess extends IProcess6<TProcessOutput, TProcessInput1, TProcessInput2, TProcessInput3, TProcessInput4, TProcessInput5, TProcessInput6>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2,
            TProcessInput3,
            TProcessInput4,
            TProcessInput5,
            TProcessInput6>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2, TProcessInput3 t3, TProcessInput4 t4, TProcessInput5 t5, TProcessInput6 t6) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2, t3, t4, t5, t6);
    }

    default <TProcess extends IProcess7<TProcessOutput, TProcessInput1, TProcessInput2, TProcessInput3, TProcessInput4, TProcessInput5, TProcessInput6, TProcessInput7>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2,
            TProcessInput3,
            TProcessInput4,
            TProcessInput5,
            TProcessInput6,
            TProcessInput7>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2, TProcessInput3 t3, TProcessInput4 t4, TProcessInput5 t5, TProcessInput6 t6, TProcessInput7 t7) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2, t3, t4, t5, t6, t7);
    }

    default <TProcess extends IProcess8<TProcessOutput, TProcessInput1, TProcessInput2, TProcessInput3, TProcessInput4, TProcessInput5, TProcessInput6, TProcessInput7, TProcessInput8>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2,
            TProcessInput3,
            TProcessInput4,
            TProcessInput5,
            TProcessInput6,
            TProcessInput7,
            TProcessInput8>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2, TProcessInput3 t3, TProcessInput4 t4, TProcessInput5 t5, TProcessInput6 t6, TProcessInput7 t7, TProcessInput8 t8) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    default <TProcess extends IProcess9<TProcessOutput, TProcessInput1, TProcessInput2, TProcessInput3, TProcessInput4, TProcessInput5, TProcessInput6, TProcessInput7, TProcessInput8, TProcessInput9>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2,
            TProcessInput3,
            TProcessInput4,
            TProcessInput5,
            TProcessInput6,
            TProcessInput7,
            TProcessInput8,
            TProcessInput9>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2, TProcessInput3 t3, TProcessInput4 t4, TProcessInput5 t5, TProcessInput6 t6, TProcessInput7 t7, TProcessInput8 t8, TProcessInput9 t9) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    default <TProcess extends IProcess10<TProcessOutput, TProcessInput1, TProcessInput2, TProcessInput3, TProcessInput4, TProcessInput5, TProcessInput6, TProcessInput7, TProcessInput8, TProcessInput9, TProcessInput10>,
            TProcessOutput,
            TProcessInput1,
            TProcessInput2,
            TProcessInput3,
            TProcessInput4,
            TProcessInput5,
            TProcessInput6,
            TProcessInput7,
            TProcessInput8,
            TProcessInput9,
            TProcessInput10>
    TProcessOutput call(Class<TProcess> process, TProcessInput1 t1, TProcessInput2 t2, TProcessInput3 t3, TProcessInput4 t4, TProcessInput5 t5, TProcessInput6 t6, TProcessInput7 t7, TProcessInput8 t8, TProcessInput9 t9, TProcessInput10 t10) throws Throwable {
        return ApplicationContextProvider.context.getBean(process).execute(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }
}
