package br.com.payment.enums;

import java.util.Arrays;

public enum PaymentType {

  PIX;

  public static PaymentType toPaymentTypeFromString(String value) {
    return Arrays.stream(PaymentType.values())
      .filter(type -> type.name().equalsIgnoreCase(value))
      .findFirst()
      .orElse(null);
  }
}
