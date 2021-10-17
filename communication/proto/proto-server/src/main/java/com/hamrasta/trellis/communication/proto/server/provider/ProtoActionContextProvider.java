package com.hamrasta.trellis.communication.proto.server.provider;

import com.hamrasta.trellis.context.action.*;
import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.http.exception.HttpException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public interface ProtoActionContextProvider {
    default <TAction extends Action<TOutput>, TOutput>
    void call(Class<TAction> action, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute();
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
    }

    default <TAction extends Action1<TOutput, TInput1>, TOutput, TInput1>
    void call(Class<TAction> action, TInput1 t1, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
    }

    default <TAction extends Action2<TOutput, TInput1, TInput2>,
            TOutput,
            TInput1,
            TInput2>
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
    }

    default <TAction extends Action3<TOutput, TInput1, TInput2, TInput3>,
            TOutput,
            TInput1,
            TInput2,
            TInput3>
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
    }

    default <TAction extends Action4<TOutput, TInput1, TInput2, TInput3, TInput4>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4>
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
    }

    default <TAction extends Action5<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4,
            TInput5>
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
    }

    default <TAction extends Action6<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6>,
            TOutput,
            TInput1,
            TInput2,
            TInput3,
            TInput4,
            TInput5,
            TInput6>
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
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
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6, t7);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
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
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, TInput8 t8, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6, t7, t8);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
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
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, TInput8 t8, TInput9 t9, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6, t7, t8, t9);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
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
    void call(Class<TAction> action, TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, TInput8 t8, TInput9 t9, TInput10 t10, StreamObserver<TOutput> stream) {
        try {
            TOutput response = ApplicationContextProvider.context.getBean(action).execute(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
            stream.onNext(response);
            stream.onCompleted();
        } catch (Throwable e) {
            String error = e instanceof HttpException ? e.toString() : e.getMessage();
            stream.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(error)));
        }
    }
}
