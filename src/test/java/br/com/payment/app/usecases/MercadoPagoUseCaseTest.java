package br.com.payment.app.usecases;

import br.com.payment.app.integration.mercadopago.MercadoPagoIntegration;
import br.com.payment.app.integration.mercadopago.request.GenerateQRCodeRequest;
import br.com.payment.app.integration.mercadopago.response.GenerateQRCodeResponse;
import br.com.payment.app.integration.orderservice.response.OrderItemResponse;
import br.com.payment.app.integration.orderservice.response.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MercadoPagoUseCaseTest {

  @Mock
  private MercadoPagoIntegration mercadoPagoIntegration;

  @InjectMocks
  private MercadoPagoUseCase useCase;

  private final String userId = "123456";
  private final String externalPosId = "POS-001";

  @BeforeEach
  void setup() {
    ReflectionTestUtils.setField(useCase, "userId", userId);
    ReflectionTestUtils.setField(useCase, "externalPosId", externalPosId);
  }

  @Test
  void shouldReturnPaymentOrderResponseWithQRCodeData() {
    var item = OrderItemResponse.builder()
      .description("Burger")
      .value(TEN)
      .quantity(2)
      .build();

    var order = OrderResponse.builder()
      .id(String.valueOf(UUID.randomUUID()))
      .items(List.of(item))
      .totalValue(TEN.multiply(BigDecimal.valueOf(2)))
      .build();

    var qrCodeResponse = GenerateQRCodeResponse.builder()
      .qrcode("QR_CODE")
      .externalOrderId("EXTERNAL_ORDER_ID")
      .build();

    when(mercadoPagoIntegration.generateQrCode(anyString(), anyString(), any(GenerateQRCodeRequest.class)))
      .thenReturn(qrCodeResponse);

    var response = useCase.pixPaymentOrder(order);

    assertEquals("QR_CODE", response.getQrcode());
    assertEquals("EXTERNAL_ORDER_ID", response.getPaymentIdentifierExternal());
    verify(mercadoPagoIntegration, times(1)).generateQrCode(eq(userId), eq(externalPosId), any());
  }
}
