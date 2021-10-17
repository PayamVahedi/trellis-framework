package com.hamrasta.trellis.message.config;

import com.hamrasta.trellis.http.helper.HttpHelper;
import com.hamrasta.trellis.message.infrastructure.MagfaRestService;

public class MessageConfig {

    public static String MAGFA_URI = "https://sms.magfa.com/api/";
    public static MagfaRestService MAGFA_REST_SERVICE = HttpHelper.getHttpInstance(MessageConfig.MAGFA_URI, MagfaRestService.class);

}
