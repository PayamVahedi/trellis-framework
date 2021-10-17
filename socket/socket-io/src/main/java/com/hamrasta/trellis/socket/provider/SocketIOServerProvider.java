package com.hamrasta.trellis.socket.provider;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.hamrasta.trellis.core.application.ApplicationContextProvider;

import java.util.UUID;

public class SocketIOServerProvider {

    protected final SocketIOServer server;

    private static SocketIOServerProvider instance;

    public static SocketIOServerProvider getInstance() {
        if (instance == null)
            instance = new SocketIOServerProvider();
        return instance;
    }

    protected SocketIOServerProvider() {
        server = ApplicationContextProvider.context.getBean(SocketIOServer.class);
    }

    protected SocketIOServer getServer() {
        return server;
    }

    public void broadcast(String eventName, Object... params) {
        server.getBroadcastOperations().sendEvent(eventName, params);
    }

    public void sendToClient(UUID client, String eventName, Object... params) {
        server.getClient(client).sendEvent(eventName, params);
    }

    public void sendToClient(String client, String eventName, Object... params) {
        sendToClient(UUID.fromString(client), eventName, params);
    }

    public <T> void addEventListener(String eventName, Class<T> clazz, DataListener<T> listener) {
        server.addEventListener(eventName, clazz, listener);
    }

    public void addConnectListener(ConnectListener listener) {
        server.addConnectListener(listener);
    }

    public void addDisconnectListener(DisconnectListener listener) {
        server.addDisconnectListener(listener);
    }

}
