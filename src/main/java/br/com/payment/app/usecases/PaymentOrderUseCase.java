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
import br.com.payment.webui.dtos.response.PaymentOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

import static br.com.payment.enums.OrderStatus.RECEIVED;
import static br.com.payment.enums.PaymentStatus.CONFIRMED;
import static br.com.payment.enums.PaymentStatus.PENDING;
import static br.com.payment.enums.PaymentType.PIX;
import static br.com.payment.enums.PaymentType.toPaymentTypeFromString;
import static br.com.payment.errors.Errors.ORDER_STATUS_IS_NOT_RECEIVED;
import static br.com.payment.errors.Errors.PAYMENT_METHOD_NOT_AVAILABLE;
import static br.com.payment.errors.Errors.PAYMENT_METHOD_NOT_FOUND;
import static br.com.payment.errors.Errors.PAYMENT_NOT_FOUND;
import static br.com.payment.utils.DateUtils.formatDateTostring;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class PaymentOrderUseCase {

  private final PaymentRepository paymentRepository;
  private final PaymentMethodRepository paymentMethodRepository;
  private final MercadoPagoUseCase mercadoPagoUseCase;
  private final OrderServiceIntegration orderServiceIntegration;

  public PaymentOrderResponse paymentOrder(PaymentOrderDTO paymentDTO) {
    var order = orderServiceIntegration.getOrderById(paymentDTO.getOrderId());

    if (!RECEIVED.equals(order.getStatus()))
      throw new BusinessException(ORDER_STATUS_IS_NOT_RECEIVED, UNPROCESSABLE_ENTITY);

    var paymentMethod = this.getPaymentMethod(paymentDTO.getPaymentMethodId());
    var paymentOrder = paymentOrder(paymentMethod.getPaymentType(), order);
    var paymentSave = paymentRepository.save(getPayment(paymentMethod, order, paymentOrder));

    paymentOrder.setPaymentId(String.valueOf(paymentSave.getId()));
    return paymentOrder;
  }

  public void paymentWebhook(PaymentWebhookDTO webhookDTO) {
    var payment = getPayment(webhookDTO.getData().getId());

    payment.setStatus(CONFIRMED.name());

    if (nonNull(webhookDTO.getCreateDate()))
      payment.setNotificationDate(formatDateTostring(ZonedDateTime.parse(webhookDTO.getCreateDate())));

    orderServiceIntegration.notifyOrderStatusUpdate(payment.getOrderId());
    paymentRepository.save(payment);
  }

  private static Payment getPayment(PaymentMethod paymentMethod, OrderResponse order, PaymentOrderResponse paymentOrder) {
    return Payment.builder()
      .paymentMethodId(paymentMethod)
      .orderId(order.getId())
      .value(order.getTotalValue())
      .status(PENDING.name())
      .paymentIdentifier(paymentOrder.getPaymentIdentifierExternal())
      .build();
  }

  private PaymentOrderResponse paymentOrder(String paymentType, OrderResponse order) {
    if (PIX.equals(toPaymentTypeFromString(paymentType)))
      return mercadoPagoUseCase.pixPaymentOrder(order);

    throw new BusinessException(PAYMENT_METHOD_NOT_AVAILABLE, UNPROCESSABLE_ENTITY);
  }

  private PaymentMethod getPaymentMethod(String paymentMethodId) {
    return paymentMethodRepository.findById(UUID.fromString(paymentMethodId))
      .orElseThrow(() -> new BusinessException(PAYMENT_METHOD_NOT_FOUND, UNPROCESSABLE_ENTITY));
  }

  private Payment getPayment(String paymentId) {
    return paymentRepository.findById(UUID.fromString(paymentId))
      .orElseThrow(() -> new BusinessException(PAYMENT_NOT_FOUND, UNPROCESSABLE_ENTITY));
  }

}
