package br.com.payment.utils;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

  @Test
  void shouldFormatDateToStringInBrazilTimezone() {
    ZonedDateTime utcDate = ZonedDateTime.of(2025, 5, 4, 10, 30, 0, 0, ZoneId.of("UTC"));

    String formattedDate = DateUtils.formatDateTostring(utcDate);

    ZonedDateTime expectedBrazilDate = utcDate.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    String expected = expectedBrazilDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

    assertEquals(expected, formattedDate);
  }
}
