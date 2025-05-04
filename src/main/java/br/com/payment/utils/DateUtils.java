package br.com.payment.utils;

import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DateUtils {

  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
  private static final String DEFAULT_ZONE = "America/Sao_Paulo";

  public static String formatDateTostring(ZonedDateTime date) {
    ZonedDateTime brazilDate = date.withZoneSameInstant(ZoneId.of(DEFAULT_ZONE));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    return brazilDate.format(formatter);
  }

}
