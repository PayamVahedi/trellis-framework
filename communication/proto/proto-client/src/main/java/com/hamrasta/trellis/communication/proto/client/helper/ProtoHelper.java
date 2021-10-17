package com.hamrasta.trellis.communication.proto.client.helper;

import com.hamrasta.trellis.communication.proto.client.provider.StubContextProvider;
import com.hamrasta.trellis.http.helper.HttpHelper;
import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.http.exception.HttpErrorMessage;
import com.hamrasta.trellis.http.exception.HttpException;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.core.message.Messages;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.AbstractStub;
import net.devh.boot.grpc.client.channelfactory.InProcessOrAlternativeChannelFactory;
import net.devh.boot.grpc.client.nameresolver.NameResolverRegistration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProtoHelper {

    public static <T extends AbstractStub<?>, TInput, TOutput> TOutput call(final String target, final Class<T> stubClass, TInput request) throws HttpException {
        try {
            ApplicationContextProvider.context.getBean(NameResolverRegistration.class);
            InProcessOrAlternativeChannelFactory channelFactory = ApplicationContextProvider.context.getBean(InProcessOrAlternativeChannelFactory.class);
            Channel channel = channelFactory.createChannel(target);
            T stub = StubContextProvider.createStub(channel, stubClass);
            Method method = stub.getClass().getMethod("execute", request.getClass());
            return (TOutput) method.invoke(stub, request);
        } catch (Exception e) {
            String message = e.getMessage();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof StatusRuntimeException)
                    message = ((StatusRuntimeException) ((InvocationTargetException) e).getTargetException()).getStatus().getDescription();
                else
                    message = ((InvocationTargetException) e).getTargetException().getMessage();
            }
            message = StringUtils.strip(message, ":").trim();
            Logger.error("ProtoServiceException", "Target: " + target + " Class: " + stubClass + " Message: " + message);
            HttpErrorMessage error = HttpHelper.parseErrorMessage(message, new HttpErrorMessage(HttpStatus.SERVICE_UNAVAILABLE, Messages.SERVICE_UNAVAILABLE.getMessage()));
            throw new HttpException(new HttpErrorMessage(error.getHttpStatus(), error.getMessage()));
        }
    }
}
