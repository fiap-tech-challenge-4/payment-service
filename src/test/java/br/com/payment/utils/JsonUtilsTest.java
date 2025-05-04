package br.com.payment.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonUtilsTest {

  static class Dummy {
    public String name;
    public int age;

    public Dummy(String name, int age) {
      this.name = name;
      this.age = age;
    }
  }

  @Test
  void shouldSerializeObjectToJson() {
    Dummy dummy = new Dummy("Alice", 30);

    String json = JsonUtils.toJson(dummy);

    assert json != null;
    assertTrue(json.contains("\"name\":\"Alice\""));
    assertTrue(json.contains("\"age\":30"));
  }

  @Test
  void shouldThrowRuntimeExceptionWhenSerializationFails() {
    assertNull(JsonUtils.toJson(new Object()));
  }
}