package com.hamrasta.trellis.message.action;

import com.hamrasta.trellis.context.action.Action2;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.message.metadata.SendMessageStatus;
import com.hamrasta.trellis.message.payload.EmbeddedData;
import com.hamrasta.trellis.message.payload.MailConfiguration;
import com.hamrasta.trellis.message.payload.SendMailRequest;
import com.hamrasta.trellis.message.payload.SendMessageResponse;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.EmailPopulatingBuilder;
import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class SendMailWithConfigurationAction extends Action2<SendMessageResponse, MailConfiguration, SendMailRequest> {

    @Override
    public SendMessageResponse execute(MailConfiguration config, SendMailRequest request) {
        try {
            EmailPopulatingBuilder builder = EmailBuilder.startingBlank().from(config.getFrom()).withRecipient(request.getEmail(), request.getEmail(), Message.RecipientType.TO).withSubject(request.getSubject()).withPlainText("").withHTMLText(request.getBody());
            Properties props = new Properties();
            props.put("mail.smtp.host", config.getHost());
            props.put("mail.smtp.port", config.getPort());
            props.put("mail.smtp.auth", config.getEnableAuthentication());
            props.put("mail.smtp.starttls.enable", config.getEnableStartTLS());
            props.put("mail.smtp.ssl.enable", config.getEnableSSL());
            if (request.getPdf() != null) {
                builder.withAttachment(request.getPdfName(), request.getPdf(), MediaType.APPLICATION_PDF_VALUE);
            }
            if (request.getImage() != null) {
                builder.withAttachment(request.getImageName(), request.getImage(), MediaType.IMAGE_PNG_VALUE);
            }
            if (request.getEmbeddedData() != null && !request.getEmbeddedData().isEmpty()) {
                for (EmbeddedData embeddedData : request.getEmbeddedData()) {
                    if (embeddedData != null && embeddedData.getData() != null) {
                        builder.withAttachment(embeddedData.getName(), embeddedData.getData(), embeddedData.getMimeType());
                    }
                }
            }
            Email email = new Email(builder);
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.getUsername(), config.getPassword());
                }
            });
            MimeMessage mimeMessage = EmailConverter.emailToMimeMessage(email, session);
            Transport.send(mimeMessage);
            return new SendMessageResponse(SendMessageStatus.SUCCESS);
        } catch (Exception e) {
            Logger.error("signMessage", e.getMessage());
            return new SendMessageResponse(SendMessageStatus.FAILED, e.getMessage());
        }
    }
}
