package com.hamrasta.trellis.message.action;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import com.hamrasta.trellis.context.action.Action2;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.message.metadata.SendMessageStatus;
import com.hamrasta.trellis.message.payload.FireBaseConfiguration;
import com.hamrasta.trellis.message.payload.SendFireBaseNotificationRequest;
import com.hamrasta.trellis.message.payload.SendMessageResponse;
import com.hamrasta.trellis.message.task.BuildFireBaseMessageTask;
import com.hamrasta.trellis.message.task.InitializeFireBaseTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SendFireBaseNotificationWithConfigurationAction extends Action2<SendMessageResponse, FireBaseConfiguration, SendFireBaseNotificationRequest> {

    @Override
    public SendMessageResponse execute(FireBaseConfiguration configuration, SendFireBaseNotificationRequest request) throws Throwable {
        try {
            FirebaseApp app = call(InitializeFireBaseTask.class, configuration);
            Message message = call(BuildFireBaseMessageTask.class, request);
            String response = FirebaseMessaging.getInstance(app).send(message);
            return new SendMessageResponse(StringUtils.isNotBlank(response) ? SendMessageStatus.SUCCESS : SendMessageStatus.FAILED);
        } catch (Exception e) {
            Logger.error("SendFireBaseNotificationException", e.getMessage());
            return new SendMessageResponse(SendMessageStatus.SUCCESS);
        }
    }

}
