package com.hamrasta.trellis.stream.producer;

import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.util.json.JsonUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class StreamBridge {

    static RabbitTemplate template;

    public static String ALL_CONSUMERS = "#";

    private static RabbitTemplate getConnection() {
        if (template == null)
            template = ApplicationContextProvider.context.getBean(RabbitTemplate.class);
        return template;
    }

    public static void send(Object object) {
        Message message = MessageBuilder
                .withBody(ObjectUtils.isEmpty(object) ? null : JsonUtil.toString(object).getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        getConnection().convertAndSend(message);
    }

    public static void send(Object object, MessageProperties properties) {
        Message message = MessageBuilder
                .withBody(ObjectUtils.isEmpty(object) ? null : JsonUtil.toString(object).getBytes())
                .andProperties(properties)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        getConnection().convertAndSend(message);
    }

    public static void send(String routingKey, Object object) {
        Message message = MessageBuilder
                .withBody(ObjectUtils.isEmpty(object) ? null : JsonUtil.toString(object).getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        getConnection().send(routingKey, message);
    }

    public static void send(String routingKey, Object object, MessageProperties properties) {
        Message message = MessageBuilder
                .withBody(ObjectUtils.isEmpty(object) ? null : JsonUtil.toString(object).getBytes())
                .andProperties(properties)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        getConnection().convertAndSend(routingKey, message);
    }

    public static void send(String exchange, String routingKey, Object object) {
        Message message = MessageBuilder
                .withBody(ObjectUtils.isEmpty(object) ? null : JsonUtil.toString(object).getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        getConnection().convertAndSend(exchange, routingKey, message);
    }

    public static void send(String exchange, String routingKey, Object object, MessageProperties properties) {
        Message message = MessageBuilder
                .withBody(ObjectUtils.isEmpty(object) ? null : JsonUtil.toString(object).getBytes())
                .andProperties(properties)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        getConnection().convertAndSend(exchange, routingKey, message);
    }
}
