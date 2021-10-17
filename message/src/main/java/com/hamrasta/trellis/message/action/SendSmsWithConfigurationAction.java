package com.hamrasta.trellis.message.action;

import com.hamrasta.trellis.context.action.Action3;
import com.hamrasta.trellis.message.metadata.MagfaMessages;
import com.hamrasta.trellis.message.metadata.SendMessageStatus;
import com.hamrasta.trellis.message.payload.SendMessageResponse;
import com.hamrasta.trellis.message.payload.SmsConfiguration;
import org.apache.axis.client.Call;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class SendSmsWithConfigurationAction extends Action3<SendMessageResponse, SmsConfiguration, String, String> {

    @Override
    public SendMessageResponse execute(SmsConfiguration configuration, String phone, String message) throws Throwable {
        try {

            final String END_POINT_URL = "https://sms.magfa.com/services/urn:SOAPSmsQueue";
            final String URN = "urn:SOAPSmsQueue";
            final String ENQUEUE_METHOD_CALL = "enqueue";
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(END_POINT_URL));
            call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));
            call.setUsername(configuration.getUsername());
            call.setPassword(configuration.getPassword());
            call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_ARRAY);
            call.setTimeout(60 * 1000);
            call.addParameter("domain", org.apache.axis.encoding.XMLType.SOAP_STRING, ParameterMode.IN);
            call.addParameter("messages", org.apache.axis.encoding.XMLType.SOAP_ARRAY, ParameterMode.IN);
            call.addParameter("recipientNumbers", org.apache.axis.encoding.XMLType.SOAP_ARRAY, ParameterMode.IN);
            call.addParameter("senderNumbers", org.apache.axis.encoding.XMLType.SOAP_ARRAY, ParameterMode.IN);
            String domain = configuration.getDomain();
            String[] messages = new String[]{message};
            String[] recipientNumbers = new String[]{phone};
            String[] senderNumbers = new String[]{configuration.getFrom()};
            Object[] params = {domain, messages, recipientNumbers, senderNumbers};
            Object[] returnValues = (Object[]) call.invoke(params);
            long value = returnValues == null || returnValues.length <= 0 ? -1 : (Long) returnValues[0];
            if (!MagfaMessages.isSuccess(value))
                return new SendMessageResponse(SendMessageStatus.FAILED, MagfaMessages.valueOf(value).getMessage());
            return new SendMessageResponse(SendMessageStatus.SUCCESS);
        } catch (Exception e) {
            System.out.println("Magfa error code: " + e.getMessage());
            return new SendMessageResponse(SendMessageStatus.FAILED, e.getMessage());
        }
    }

}
