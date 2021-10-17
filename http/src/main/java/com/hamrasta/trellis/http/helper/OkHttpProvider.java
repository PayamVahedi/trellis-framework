package com.hamrasta.trellis.http.helper;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

import java.util.concurrent.TimeUnit;

public class OkHttpProvider {
    public static OkHttpClient client;
    static int defaultConnectTimout = 300;
    static int defaultWriteTimout = 300;
    static int defaultReadTimout = 300;

    public OkHttpProvider() {
    }

    public static OkHttpClient getInstance() {
        return getInstance(defaultConnectTimout, defaultWriteTimout, defaultReadTimout);
    }

    public static OkHttpClient getInstance(int connectTimeout, int writeTimeout, int readTimeout) {
        if (client == null ||
                TimeUnit.SECONDS.toMillis(connectTimeout) != client.connectTimeoutMillis() ||
                TimeUnit.SECONDS.toMillis(writeTimeout) != client.writeTimeoutMillis() ||
                TimeUnit.SECONDS.toMillis(readTimeout) != client.readTimeoutMillis()
        )
            client = (new Builder()).connectTimeout(connectTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).connectionPool(new ConnectionPool(5, 10L, TimeUnit.SECONDS)).build();
        return client;
    }

    static {
        client = getInstance();
    }
}
