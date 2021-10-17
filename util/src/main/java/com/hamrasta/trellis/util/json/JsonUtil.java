package com.hamrasta.trellis.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.core.message.Messages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hamrasta.trellis.http.exception.NotAcceptableException;
import com.hamrasta.trellis.http.exception.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class JsonUtil {

    static ObjectMapper mapper = new ObjectMapper();

    public static String toString(Object value) {
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

    public static <T> T toObject(String value, Class<T> valueType) {
        try {
            return getMapper().readValue(value, valueType);
        } catch (IOException e) {
            Logger.error("JsonParseException", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(Object value, Class<T> valueType) {
        try {
            return getMapper().convertValue(value, valueType);
        } catch (Exception e) {
            Logger.error("JsonParseException", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String value, TypeReference<T> valueTypeRef) {
        try {
            return getMapper().readValue(value, valueTypeRef);
        } catch (IOException e) {
            Logger.error("JsonParseException", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String value, JavaType javaType) {
        try {
            return getMapper().readValue(value, javaType);
        } catch (IOException e) {
            Logger.error("JsonParseException", e.getMessage());
            return null;
        }
    }

    public static <T> void writeToFile(String path, T value) throws NotAcceptableException {
        try {
            File file = new File(path);
            File dir = file.getParentFile();
            if (!dir.exists())
                dir.mkdirs();
            String json = toString(value);
            Files.write(Paths.get(path), json.getBytes());
        } catch (IOException e) {
            Logger.error("Write file", e.getMessage());
            throw new NotAcceptableException(Messages.ERROR_ON_SAVE_FILE);
        }
    }

    public static <T> T readFromFile(String path, Class<T> valueType) throws NotFoundException, NotAcceptableException {
        File file = new File(path);
        if (!file.exists() || !file.isFile())
            throw new NotFoundException(Messages.FILE_NOT_FOUND);
        try {
            return getMapper().readValue(file, valueType);
        } catch (IOException e) {
            Logger.error("Read file", e.getMessage());
            throw new NotAcceptableException(Messages.ERROR_ON_READ_FILE);
        }
    }
}
