package com.hamrasta.trellis.ui.web.infrastructure;

import com.hamrasta.trellis.ui.web.payload.GoogleCaptchaVerifyResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GoogleCaptchaService {
    @FormUrlEncoded
    @POST("/recaptcha/api/siteverify")
    Call<GoogleCaptchaVerifyResponse> verify(@Field("secret") String secret, @Field("response") String response, @Field("remoteip") String ip);
}
