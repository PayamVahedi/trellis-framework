package com.hamrasta.trellis.communication.proto.client.provider;

import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.http.exception.NotFoundException;
import io.grpc.Channel;
import io.grpc.stub.AbstractStub;
import net.devh.boot.grpc.client.stubfactory.FallbackStubFactory;
import net.devh.boot.grpc.client.stubfactory.StubFactory;
import org.springframework.beans.BeanInstantiationException;

import java.util.ArrayList;
import java.util.List;

public class StubContextProvider {

    public static <T extends AbstractStub<?>> T createStub(final Channel channel, final Class<T> stubClass) throws NotFoundException {
        final StubFactory factory = getStubFactories().stream()
                .filter(stubFactory -> stubFactory.isApplicable(stubClass))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Unsupported stub type: " + stubClass.getName() + " -> Please report this issue."));

        try {
            return (T) factory.createStub(stubClass, channel);
        } catch (final Exception exception) {
            throw new BeanInstantiationException(stubClass, "Failed to create gRPC stub of type " + stubClass.getName(), exception);
        }
    }

    private static List<StubFactory> getStubFactories() {
        List<StubFactory> stubFactories = new ArrayList<>(ApplicationContextProvider.context.getBeansOfType(StubFactory.class).values());
        stubFactories.add(new FallbackStubFactory());
        return stubFactories;
    }
}
