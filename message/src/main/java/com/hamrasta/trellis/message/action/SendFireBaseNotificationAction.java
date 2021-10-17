package com.hamrasta.trellis.message.action;

import com.hamrasta.trellis.context.action.Action1;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.message.metadata.SendMessageStatus;
import com.hamrasta.trellis.message.payload.FireBaseConfiguration;
import com.hamrasta.trellis.message.payload.SendFireBaseNotificationRequest;
import com.hamrasta.trellis.message.payload.SendMessageResponse;
import org.springframework.stereotype.Service;

@Service
public class SendFireBaseNotificationAction extends Action1<SendMessageResponse, SendFireBaseNotificationRequest> {

    @Override
    public SendMessageResponse execute(SendFireBaseNotificationRequest request) throws Throwable {
        try {
            return call(SendFireBaseNotificationWithConfigurationAction.class, FireBaseConfiguration.getFromApplicationConfig(), request);
        } catch (Exception e) {
            Logger.error("SendFireBaseNotificationException", e.getMessage());
            return new SendMessageResponse(SendMessageStatus.SUCCESS);
        }
    }

}
