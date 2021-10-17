package com.hamrasta.trellis.message.task;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.hamrasta.trellis.context.task.Task1;
import com.hamrasta.trellis.http.exception.BadRequestException;
import com.hamrasta.trellis.core.message.Messages;
import com.hamrasta.trellis.message.payload.FireBaseConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class InitializeFireBaseTask extends Task1<FirebaseApp, FireBaseConfiguration> {

    @Override
    public FirebaseApp execute(FireBaseConfiguration configuration) throws Throwable {
        if (StringUtils.isBlank(configuration.getCredential()))
            throw new BadRequestException(Messages.CREDENTIAL_IS_REQUIRED);
        FirebaseApp app = FirebaseApp.getApps().stream().filter(x -> x.getName().equalsIgnoreCase(configuration.getName())).findFirst().orElse(null);
        if (app != null)
            return app;
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(IOUtils.toInputStream(configuration.getCredential(), StandardCharsets.UTF_8))).build();
        return FirebaseApp.initializeApp(options, configuration.getName());
    }
}
