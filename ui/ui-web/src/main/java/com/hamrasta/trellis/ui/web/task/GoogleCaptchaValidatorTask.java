package com.hamrasta.trellis.ui.web.task;

import com.hamrasta.trellis.http.helper.HttpHelper;
import com.hamrasta.trellis.context.task.Task2;
import com.hamrasta.trellis.ui.web.helper.IWebHelper;
import com.hamrasta.trellis.ui.web.infrastructure.GoogleCaptchaService;
import com.hamrasta.trellis.ui.web.payload.GoogleCaptchaVerifyResponse;
import org.springframework.stereotype.Service;

@Service
public class GoogleCaptchaValidatorTask extends Task2<GoogleCaptchaVerifyResponse, String, String> implements IWebHelper {

    @Override
    public GoogleCaptchaVerifyResponse execute(String secret, String token) throws Throwable {
        return HttpHelper.call(HttpHelper.getHttpInstance("https://www.google.com", GoogleCaptchaService.class).verify(secret, token, getIp()));
    }
}
