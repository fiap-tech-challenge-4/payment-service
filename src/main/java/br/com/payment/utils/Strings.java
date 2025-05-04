package br.com.payment.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Strings {

  public static boolean containsAny(String text, String... parts) {
    if (text != null) {
      String value = text.toLowerCase();
      for (String part : parts) {
        if (part != null && value.contains(part.toLowerCase())) return true;
      }
    }
    return false;
  }

}
