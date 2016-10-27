package com.analyticsgo.model.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public abstract class JsonConverter<T> implements AttributeConverter<T, String> {

  private TypeReference<T> typeRef;

  private ObjectMapper objectMapper = createObjectMapper();

  protected abstract TypeReference<T> createTypeRef();

  public static ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    return objectMapper;
  }

  @Override
  public String convertToDatabaseColumn(T attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("convertToDatabaseColumn failed", e);
    }
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    try {
      if (typeRef == null) {
        typeRef = createTypeRef();
      }
      return objectMapper.readValue(dbData, typeRef);
    } catch (IOException e) {
      throw new RuntimeException("convertToEntityAttribute failed", e);
    }
  }

}
