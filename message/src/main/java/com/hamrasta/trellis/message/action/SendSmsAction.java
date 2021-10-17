package com.hamrasta.trellis.message.action;

import com.hamrasta.trellis.context.action.Action2;
import com.hamrasta.trellis.message.payload.SendMessageResponse;
import com.hamrasta.trellis.message.payload.SmsConfiguration;
import org.springframework.stereotype.Service;

@Service
public class SendSmsAction extends Action2<SendMessageResponse, String, String> {

    @Override
    public SendMessageResponse execute(String phone, String message) throws Throwable {
        return call(SendSmsWithConfigurationAction.class, SmsConfiguration.getFromApplicationConfig(), phone, message);
    }

}
