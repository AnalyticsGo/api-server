package com.analyticsgo.util;

import com.analyticsgo.config.BeanConfig;
import com.analyticsgo.test.UnitTest;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonUtilsTest extends UnitTest {

  private JsonUtils jsonUtils;

  @Before
  public void setUp() {
    BeanConfig beanConfig = new BeanConfig();
    jsonUtils = new JsonUtils(beanConfig.objectMapper());
  }

  @Test
  public void testNumberToJson() {
    assertThat(jsonUtils.toJson(1), is("1"));
  }

  @Test
  public void testJsonToNumber() {
    assertThat(jsonUtils.fromJson("1", Integer.class), is(1));
  }

  @Test
  public void testStringToJson() {
    assertThat(jsonUtils.toJson("abc"), is("\"abc\""));
  }

  @Test
  public void testJsonToString() {
    assertThat(jsonUtils.fromJson("\"abc\"", String.class), is("abc"));
  }

  @Test
  public void testBooleanToJson() {
    assertThat(jsonUtils.toJson(true), is("true"));
    assertThat(jsonUtils.toJson(false), is("false"));
  }

  @Test
  public void testJsonToBoolean() {
    assertThat(jsonUtils.fromJson("true", Boolean.class), is(true));
    assertThat(jsonUtils.fromJson("false", Boolean.class), is(false));
  }

  @Test
  public void testNullToJson() {
    assertThat(jsonUtils.toJson(null), is("null"));
  }

  @Test
  public void testJsonToNull() {
    assertThat(jsonUtils.fromJson("null", Object.class), nullValue());
  }

  @Test
  public void testDateToJson() throws Exception {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    assertThat(jsonUtils.toJson(dateFormat.parse("20161027_224018")),
        containsString("2016-10-27T22:40:18.000"));
  }

  @Test
  public void testListToJson() {
    assertThat(jsonUtils.toJson(Arrays.asList(1, 2, 3)), is("[1,2,3]"));
  }

  @Test
  public void testJsonToList() {
    assertThat(jsonUtils.fromJson("[1,2,3]", List.class), is(Arrays.asList(1, 2, 3)));
  }

  @Test
  public void testMapToJson() {
    Map<String, Object> map = new HashMap<>();
    map.put("a", 1);
    map.put("b", 2);
    // Map keys are unordered
    assertThat(jsonUtils.toJson(map), anyOf(is("{\"a\":1,\"b\":2}"), is("{\"b\":2,\"a\":1}")));
  }

  @Test
  public void testJsonToMap() {
    TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
    };
    Map<String, Object> map = jsonUtils.fromJson("{\"a\":1,\"b\":2}", typeRef);
    assertThat(map.size(), is(2));
    assertThat(map, hasEntry("a", 1));
    assertThat(map, hasEntry("b", 2));
  }

  @Test
  public void testObjectToJson() {
    // Object keys are in alphabetic order
    assertThat(jsonUtils.toJson(TestJsonObject.build("a", false, 1)),
        is("{\"booleanField\":false,\"intField\":1,\"stringField\":\"a\"}"));
  }

  @Test
  public void testJsonToObject() {
    assertThat(jsonUtils.fromJson("{\"stringField\":\"a\",\"booleanField\":false,\"intField\":1}",
        TestJsonObject.class), is(TestJsonObject.build("a", false, 1)));
  }

  @Test
  public void testJsonToObjectWithUnknownField() {
    assertThat(jsonUtils.fromJson(
        "{\"stringField\":\"a\",\"booleanField\":false,\"intField\":1,\"unknown\":3}",
        TestJsonObject.class), is(TestJsonObject.build("a", false, 1)));
  }

  @Test
  public void testEmptyObjectToJson() {
    // Object keys are in alphabetic order
    assertThat(jsonUtils.toJson(new TestJsonObject()), is("{}"));
  }

  @Test
  public void testJsonToEmptyObject() {
    assertThat(jsonUtils.fromJson("{}", TestJsonObject.class), is(new TestJsonObject()));
  }

  @Test
  public void testNullFieldToJson() {
    assertThat(jsonUtils.toJson(TestJsonObject.build(null, true, null)),
        is("{\"booleanField\":true}"));
  }

  @EqualsAndHashCode
  public static class TestJsonObject {

    @Setter
    @Getter
    private String stringField;

    @Setter
    @Getter
    private Boolean booleanField;

    @Setter
    @Getter
    private Integer intField;

    public static TestJsonObject build(String stringField, Boolean booleanField, Integer intField) {
      TestJsonObject obj = new TestJsonObject();
      obj.setStringField(stringField);
      obj.setBooleanField(booleanField);
      obj.setIntField(intField);
      return obj;
    }

  }

}
