package br.com.payment.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringsTest {

  @Test
  void shouldReturnTrueWhenTextContainsAnyPart() {
    assertTrue(Strings.containsAny("Hello World", "world", "test"));
    assertTrue(Strings.containsAny("Java Programming", "gram", "script"));
  }

  @Test
  void shouldReturnFalseWhenTextDoesNotContainAnyPart() {
    assertFalse(Strings.containsAny("Hello World", "foo", "bar"));
  }

  @Test
  void shouldReturnFalseWhenTextIsNull() {
    assertFalse(Strings.containsAny(null, "hello"));
  }

  @Test
  void shouldReturnFalseWhenAllPartsAreNull() {
    assertFalse(Strings.containsAny("hello", null, null));
  }

  @Test
  void shouldIgnoreCaseWhenComparing() {
    assertTrue(Strings.containsAny("Hello World", "WORLD"));
    assertTrue(Strings.containsAny("Hello World", "HELLO"));
  }

  @Test
  void shouldReturnFalseWhenPartsIsEmpty() {
    assertFalse(Strings.containsAny("hello"));
  }

  @Test
  void shouldHandleNullValuesInsideParts() {
    assertTrue(Strings.containsAny("example", null, "exam"));
  }

}
