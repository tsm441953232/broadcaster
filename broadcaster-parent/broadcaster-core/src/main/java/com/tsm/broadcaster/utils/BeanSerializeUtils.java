package com.tsm.broadcaster.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeanSerializeUtils {
    public static final ObjectMapper mapper = new ObjectMapper();

    public BeanSerializeUtils() {
    }

    public static <T> T deserializeData(String jsonStr, Class<T> type) {
        if (jsonStr == null) {
            return null;
        } else if (type == null) {
            return null;
        } else {
            try {
                return mapper.readValue(jsonStr, type);
            } catch (IOException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static <T> List<T> deserializeDataList(String jsonStr, Class<T> type) {
        if (jsonStr == null) {
            return null;
        } else if (type == null) {
            return null;
        } else {
            try {
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{type});
                return (List)mapper.readValue(jsonStr, javaType);
            } catch (IOException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static String serializeWithNullValueNode(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String serializeWithDefaultPrettyPrinter(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
