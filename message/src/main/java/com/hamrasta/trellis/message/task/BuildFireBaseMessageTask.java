package com.hamrasta.trellis.message.task;

import com.google.firebase.messaging.*;
import com.hamrasta.trellis.context.task.Task1;
import com.hamrasta.trellis.message.metadata.FireBaseNotificationParameter;
import com.hamrasta.trellis.message.metadata.FireBasePlatform;
import com.hamrasta.trellis.message.payload.SendFireBaseNotificationRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class BuildFireBaseMessageTask extends Task1<Message, SendFireBaseNotificationRequest> {

    @Override
    public Message execute(SendFireBaseNotificationRequest request) {
        Message.Builder messageBuilder = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .setImage(request.getImageUrl())
                        .build());
        if (request.getPlatform() != null && request.getPlatform().isIn(FireBasePlatform.ALL, FireBasePlatform.WEB))
            messageBuilder.setWebpushConfig(WebpushConfig.builder().setNotification(WebpushNotification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getBody())
                    .setImage(request.getImageUrl())
                    .build())
                    .build());
        if (request.getPlatform() != null && request.getPlatform().isIn(FireBasePlatform.ALL, FireBasePlatform.ANDROID))
            messageBuilder.setAndroidConfig(AndroidConfig.builder()
                    .setTtl(Duration.ofMinutes(2).toMillis())
                    .setCollapseKey(request.getTopic())
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setNotification(AndroidNotification
                            .builder()
                            .setSound(FireBaseNotificationParameter.SOUND.getValue())
                            .setColor(FireBaseNotificationParameter.COLOR.getValue())
                            .setTag(request.getTopic())
                            .build())
                    .build());
        if (request.getPlatform() != null && request.getPlatform().isIn(FireBasePlatform.ALL, FireBasePlatform.IOS))
            messageBuilder.setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setCategory(request.getTopic())
                    .setThreadId(request.getTopic())
                    .build()
            ).build());
        if (StringUtils.isNoneEmpty(request.getTopic()))
            messageBuilder.setTopic(request.getTopic());
        else if (StringUtils.isNoneEmpty(request.getToken()))
            messageBuilder.setToken(request.getToken());
        if (request.getData() != null && !request.getData().isEmpty())
            messageBuilder.putAllData(request.getData());
        return messageBuilder.build();
    }

}
