package com.airbyte.charity.common;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;
import java.util.TreeMap;

@Converter
public class FileConverter implements AttributeConverter<Map<String, String>, String> {

    private static final String SPLIT_INDEX = "@@";
    private static final String SPLIT_MAP = ";";

    @Override
    public String convertToDatabaseColumn(Map<String, String> fileMap) {
        StringBuilder result = new StringBuilder();
        if (fileMap != null && !fileMap.isEmpty()) {
            for (String key : fileMap.keySet()) {
                String value = fileMap.get(key);
                result.append(SPLIT_INDEX).append(key).append(SPLIT_MAP).append(value);
            }
        }
        return result.toString();
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String fileMap) {
        String[] list = fileMap.split("@@");
        Map<String, String> resultMap = new TreeMap<>();
        for (String index : list) {
            if (!index.isEmpty()) {
                String[] value = index.split(SPLIT_MAP);
                value[0] = value[0].replace("$", "");
                resultMap.put(value[0], value[1]);
            }
        }
        return resultMap;
    }
}
