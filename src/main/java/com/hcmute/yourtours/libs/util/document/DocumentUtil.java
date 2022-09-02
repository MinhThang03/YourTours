package com.hcmute.yourtours.libs.util.document;

import org.json.JSONException;
import com.hcmute.yourtours.libs.util.document.annotation.ExportConvert;
import com.hcmute.yourtours.libs.util.document.converter.GenerateConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentUtil {
    public static <T extends BaseMappingData> Map<String, Object> toMap(T objects) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Map<String, Object> map = new HashMap<>();

        Field[] fields = objects.getClass().getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            Object value = field.get(objects);
            Map<String, Object> childMap;

            if (value instanceof ArrayList) {
                List<Map<String, Object>> list = toList((List<?>) value);
                for (int i = 0; i < list.size(); i++) {
                    for (Map.Entry<String, Object> itemArray : list.get(i).entrySet()) {
                        map.put(key + "[" + i + "]." + itemArray.getKey(), itemArray.getValue());
                    }
                }
            } else if (value instanceof BaseMappingData) {
                childMap = toMap((BaseMappingData) value);
                for (Map.Entry<String, Object> itemObject : childMap.entrySet()) {
                    Object convertValue = itemObject.getValue();
                    map.put(key + "." + itemObject.getKey(), convertValue);
                }
            } else {
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof ExportConvert) {
                        ExportConvert convert = (ExportConvert) annotation;
                        Class<GenerateConverter<?, ?>> exportConverter = (Class<GenerateConverter<?, ?>>) convert.converter();
                        GenerateConverter exportConverter1 = exportConverter.getDeclaredConstructor().newInstance();
                        value = exportConverter1.convert(value);
                    }
                }

                map.put(key, value);
            }
        }
        return map;
    }

    public static List<Map<String, Object>> toList(List<?> array) throws JSONException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            Map<String, Object> mapArray = new HashMap<>();
            Map<String, Object> subObjectMap = new HashMap<>();

            List<Map<String, Object>> subListMap;
            if (value instanceof ArrayList) {
                subListMap = toList((ArrayList) value);
                for (Map<String, Object> map : subListMap) {
                    for (Map.Entry<String, Object> item : map.entrySet()) {
                        mapArray.put("[" + i + "]." + item.getKey(), item.getValue());
                    }
                }
            } else if (value instanceof BaseMappingData) {
                subObjectMap = toMap((BaseMappingData) value);
            }

            mapArray.putAll(subObjectMap);
            mapList.add(mapArray);
        }
        return mapList;
    }
}
