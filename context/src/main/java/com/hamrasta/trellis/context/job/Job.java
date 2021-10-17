package com.hamrasta.trellis.context.job;

import com.hamrasta.trellis.context.provider.ActionContextProvider;
import com.hamrasta.trellis.util.mapper.ModelMapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Job implements ModelMapper, ActionContextProvider {

    private ExecutorService executorService;

    protected ExecutorService getExecutorService() {
        return getExecutorService(10);
    }

    protected ExecutorService getExecutorService(int poolCount) {
        return executorService = executorService == null ? Executors.newFixedThreadPool(poolCount) : executorService;
    }

}
