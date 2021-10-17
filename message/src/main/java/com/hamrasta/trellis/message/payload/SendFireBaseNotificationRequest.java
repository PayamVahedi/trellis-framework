package com.hamrasta.trellis.message.payload;

import com.hamrasta.trellis.core.payload.RequestMessage;
import com.hamrasta.trellis.message.metadata.FireBasePlatform;

import java.util.Map;

public class SendFireBaseNotificationRequest extends RequestMessage {
    private FireBasePlatform platform = FireBasePlatform.ALL;

    private String title;

    private String body;

    private String topic;

    private String token;

    private String imageUrl;

    private Map<String, String> data;

    public FireBasePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(FireBasePlatform platform) {
        if (platform != null)
            this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SendFireBaseNotificationRequest() {
    }

    public SendFireBaseNotificationRequest(FireBasePlatform platform, String title, String body, String topic, String token, String imageUrl, Map<String, String> data) {
        this.platform = platform;
        this.title = title;
        this.body = body;
        this.topic = topic;
        this.token = token;
        this.imageUrl = imageUrl;
        this.data = data;
    }
}
