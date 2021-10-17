package com.hamrasta.trellis.http.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.hamrasta.trellis.core.log.Logger;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class JsonFactory extends Converter.Factory {
    static JsonFactory instance;

    static ObjectMapper mapper = new ObjectMapper();

    static String toString(Object value) {
        try {
            ObjectMapper Obj = new ObjectMapper();
            return Obj.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            Logger.error("JsonProcessingException", e.getMessage());
            return "";
        }
    }

    private static ObjectMapper getMapper() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return mapper;
    }

    static <T> T toObject(String value, Class<T> valueType) {
        try {
            return getMapper().readValue(value, valueType);
        } catch (IOException e) {
            Logger.error("JsonParseException", e.getMessage());
            return null;
        }
    }

    static <T> T toObject(String value, JavaType javaType) {
        try {
            return getMapper().readValue(value, javaType);
        } catch (IOException e) {
            Logger.error("JsonParseException", e.getMessage());
            return null;
        }
    }

    public static JsonFactory getInstance() {
        if (instance == null)
            instance = new JsonFactory();
        return instance;
    }

    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<>() {
            @Nullable
            @Override
            public Object convert(ResponseBody body) {
                try {
                    String response = body.string();
                    body.close();
                    return toObject(response, mapper.getTypeFactory().constructType(type));
                } catch (Exception e) {
                    body.close();
                    Logger.error("ClassNotFoundException", e.getMessage());
                    return null;
                }
            }
        };
    }

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return o -> RequestBody.create(MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE), toString(o));
    }
}