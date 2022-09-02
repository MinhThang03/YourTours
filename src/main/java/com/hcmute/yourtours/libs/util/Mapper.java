package com.hcmute.yourtours.libs.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;

import java.io.IOException;

public class Mapper {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .addHandler(
                    new DeserializationProblemHandler() {
                        @Override
                        public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert, String failureMsg) throws IOException {
                            return null;
                        }

                        @Override
                        public Object handleWeirdNumberValue(DeserializationContext ctxt, Class<?> targetType, Number valueToConvert, String failureMsg) throws IOException {
                            return null;
                        }
                    }
            );

    static {
        mapper.findAndRegisterModules();
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return mapper.convertValue(fromValue, toValueType);
    }

    public static String toString(Object serializableObject) throws InvalidException {
        try {
            return mapper.writeValueAsString(serializableObject);
        } catch (JsonProcessingException e) {
            throw new InvalidException(ErrorCode.CONVERT_TO_STRING_ERROR);
        }
    }
}
