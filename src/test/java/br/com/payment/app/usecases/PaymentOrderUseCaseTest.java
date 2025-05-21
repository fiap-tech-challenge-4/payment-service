package br.com.payment.app.usecases;

import br.com.payment.app.entities.Payment;
import br.com.payment.app.entities.PaymentMethod;
import br.com.payment.app.exception.BusinessException;
import br.com.payment.app.integration.orderservice.OrderServiceIntegration;
import br.com.payment.app.integration.orderservice.response.OrderResponse;
import br.com.payment.app.repositories.PaymentMethodRepository;
import br.com.payment.app.repositories.PaymentRepository;
import br.com.payment.webui.dtos.PaymentOrderDTO;
import br.com.payment.webui.dtos.PaymentWebhookDTO;
import br.com.payment.webui.dtos.WebhookDataDTO;
import br.com.payment.webui.dtos.response.PaymentOrderResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static br.com.payment.enums.OrderStatus.COMPLETED;
import static br.com.payment.enums.OrderStatus.RECEIVED;
import static br.com.payment.enums.PaymentStatus.CONFIRMED;
import static br.com.payment.enums.PaymentStatus.PENDING;
import static br.com.payment.enums.PaymentType.PIX;
import static br.com.payment.errors.Errors.ORDER_STATUS_IS_NOT_RECEIVED;
import static br.com.payment.errors.Errors.PAYMENT_METHOD_NOT_AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentOrderUseCaseTest {

  @InjectMocks
  private PaymentOrderUseCase useCase;
  @Mock
  private PaymentRepository paymentRepository;
  @Mock
  private PaymentMethodRepository paymentMethodRepository;
  @Mock
  private MercadoPagoUseCase mercadoPagoUseCase;
  @Mock
  private OrderServiceIntegration orderServiceIntegration;

  @Test
  void shouldCreatePixPaymentOrderSuccessfully() {
    var order = OrderResponse.builder()
      .id("order-id")
      .status(RECEIVED)
      .totalValue(BigDecimal.valueOf(100))
      .build();

    var paymentMethod = PaymentMethod.builder()
      .id(UUID.randomUUID())
      .paymentType(PIX.name())
      .build();

    var paymentOrderResponse = PaymentOrderResponse.builder()
      .paymentIdentifierExternal("123456")
      .qrcode("qr-code")
      .build();

    var payment = Payment.builder()
      .id(UUID.randomUUID())
      .build();

    when(orderServiceIntegration.getOrderById("order-id")).thenReturn(order);
    when(paymentMethodRepository.findById(any())).thenReturn(Optional.of(paymentMethod));
    when(mercadoPagoUseCase.pixPaymentOrder(any())).thenReturn(paymentOrderResponse);
    when(paymentRepository.save(any())).thenReturn(payment);

    var response = useCase.paymentOrder(
      PaymentOrderDTO.builder()
        .orderId("order-id")
        .paymentMethodId(paymentMethod.getId().toString())
        .build()
    );

    assertEquals("123456", response.getPaymentIdentifierExternal());
    assertEquals("qr-code", response.getQrcode());
    verify(paymentRepository, times(1)).save(any(Payment.class));
  }

  @Test
  void shouldThrowWhenPaymentTypeIsInvalid() {
    var order = OrderResponse.builder()
      .id("order-id")
      .status(RECEIVED)
      .totalValue(BigDecimal.valueOf(100))
      .build();

    var paymentMethod = PaymentMethod.builder()
      .id(UUID.randomUUID())
      .paymentType("Test")
      .build();

    when(orderServiceIntegration.getOrderById("order-id")).thenReturn(order);
    when(paymentMethodRepository.findById(any())).thenReturn(Optional.of(paymentMethod));

    var paymentOrderDTO = PaymentOrderDTO.builder()
      .orderId("order-id")
      .paymentMethodId(paymentMethod.getId().toString())
      .build();

    var ex = assertThrows(BusinessException.class, () -> useCase.paymentOrder(paymentOrderDTO));

    assertEquals(PAYMENT_METHOD_NOT_AVAILABLE, ex.getMessage());
    verify(paymentRepository, never()).save(any());
  }

  @Test
  void shouldThrowWhenOrderStatusIsNotReceived() {
    var order = OrderResponse.builder()
      .id("order-id")
      .status(COMPLETED)
      .build();

    when(orderServiceIntegration.getOrderById("order-id")).thenReturn(order);

    var paymentOrderDTO = PaymentOrderDTO.builder()
      .orderId("order-id")
      .paymentMethodId(UUID.randomUUID().toString())
      .build();

    var ex = assertThrows(BusinessException.class, () -> useCase.paymentOrder(paymentOrderDTO));

    assertEquals(ORDER_STATUS_IS_NOT_RECEIVED, ex.getMessage());
    verify(paymentRepository, never()).save(any());
  }

  @Test
  void shouldConfirmPaymentOnWebhook() {
    var paymentId = UUID.randomUUID().toString();
    var payment = Payment.builder()
      .id(UUID.fromString(paymentId))
      .orderId("order-id")
      .status(PENDING.name())
      .build();

    var webhookDTO = PaymentWebhookDTO.builder()
      .data(WebhookDataDTO.builder().id(paymentId).build())
      .createDate(ZonedDateTime.now().toString())
      .build();

    when(paymentRepository.findById(UUID.fromString(paymentId))).thenReturn(Optional.of(payment));

    useCase.paymentWebhook(webhookDTO);

    assertEquals(CONFIRMED.name(), payment.getStatus());
    assertNotNull(payment.getNotificationDate());
    verify(orderServiceIntegration).notifyOrderStatusUpdate("order-id");
    verify(paymentRepository).save(payment);
  }

}
