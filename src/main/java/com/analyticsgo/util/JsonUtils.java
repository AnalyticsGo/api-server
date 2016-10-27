package com.analyticsgo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

  private final ObjectMapper objectMapper;

  public JsonUtils(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String toJson(Object value) {
    try {
      return objectMapper.writeValueAsString(value);
    } catch (Exception e) {
      throw new RuntimeException("Convert object to json failed", e);
    }
  }

  public <T> T fromJson(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (Exception e) {
      throw new RuntimeException("Convert json to object with Class failed", e);
    }
  }

  public <T> T fromJson(String json, TypeReference<T> typeRef) {
    try {
      return objectMapper.readValue(json, typeRef);
    } catch (Exception e) {
      throw new RuntimeException("Convert json to object with TypeReference failed", e);
    }
  }

}
