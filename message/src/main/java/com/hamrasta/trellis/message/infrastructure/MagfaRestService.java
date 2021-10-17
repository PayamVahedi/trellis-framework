package com.hamrasta.trellis.message.infrastructure;

import com.hamrasta.trellis.message.payload.MagfaMesageResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.Set;

public interface MagfaRestService {

    @FormUrlEncoded
    @POST("/http/sms/v2")
    Call<MagfaMesageResponse> send(@Header("Authorization") String authorization, @Field("senders") Set<String> senders, @Field("recipients") Set<String> recipients, @Field("messages") Set<String> messages);

}
