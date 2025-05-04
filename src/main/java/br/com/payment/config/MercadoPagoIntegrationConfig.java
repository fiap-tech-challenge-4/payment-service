package br.com.payment.config;

import br.com.payment.app.exception.BusinessException;
import br.com.payment.app.integration.mercadopago.response.ErrorMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static br.com.payment.config.MapperErrorsMercadoPago.GENERIC_ERROR;
import static br.com.payment.utils.Strings.containsAny;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class MercadoPagoIntegrationConfig implements ErrorDecoder {

  @Value("${mercado-pago.secret-token}")
  private String token;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return template -> template.header("Authorization", "Bearer " + token);
  }

  @Override
  public Exception decode(String s, Response response) {

    ErrorMessageResponse message = null;
    try (InputStream bodyIs = response.body().asInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
      message = mapper.readValue(bodyIs, ErrorMessageResponse.class);
    } catch (IOException e) {
      return new Exception(e.getMessage());
    }

    if (response.status() == 400)
      throw new BusinessException(MapperErrorsMercadoPago.getMessage(message.getMessage()), UNPROCESSABLE_ENTITY);

    throw new BusinessException(String.format(GENERIC_ERROR.getMessage(), message.getMessage()), UNPROCESSABLE_ENTITY);
  }
}

@Getter
@NoArgsConstructor
@AllArgsConstructor
enum MapperErrorsMercadoPago {

  GENERIC_ERROR("", "Generic error in payment integration. Error: %s"),
  INVALID_TOTAL_VALUE("Invalid total amount. Total amount must be the sum of the total amount of items and the cash out amount.", "Erro na integração de pagamento. Valor total é inválido, a soma do valor total dos itens é diferente do valor total da capa"),
  TOKEN_BAD_FORMAT("Malformed access_token", "Token bad format");

  private String originalMessage;
  private String message;

  public static String getMessage(String message) {
    return Arrays.stream(values())
      .filter(f -> containsAny(f.getOriginalMessage(), message))
      .map(x -> x.message)
      .findFirst()
      .orElse(String.format(GENERIC_ERROR.getMessage(), message));
  }

}
