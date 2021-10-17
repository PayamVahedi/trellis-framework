package com.hamrasta.trellis.socket.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SocketIOServerProperties.class)
public class SocketIOServerConfiguration {

    private final Configuration configuration;

    public SocketIOServerConfiguration(SocketIOServerProperties properties) {
        this.configuration = new Configuration();
        this.configuration.setHostname(properties.getHost());
        this.configuration.setPort(properties.getPort());
        this.configuration.getSocketConfig().setReuseAddress(true);
    }

    @Bean
    public SocketIOServer socketIOServer() {
        SocketIOServer socketIOServer = new SocketIOServer(this.configuration);
        socketIOServer.start();
        return socketIOServer;
    }
}