package com.hamrasta.trellis.message.action;

import com.hamrasta.trellis.context.action.Action1;
import com.hamrasta.trellis.message.payload.MailConfiguration;
import com.hamrasta.trellis.message.payload.SendMailRequest;
import com.hamrasta.trellis.message.payload.SendMessageResponse;
import org.springframework.stereotype.Service;

@Service
public class SendMailAction extends Action1<SendMessageResponse, SendMailRequest> {

    @Override
    public SendMessageResponse execute(SendMailRequest request) throws Throwable {
        return call(SendMailWithConfigurationAction.class, MailConfiguration.getFromApplicationConfig(), request);
    }

}
