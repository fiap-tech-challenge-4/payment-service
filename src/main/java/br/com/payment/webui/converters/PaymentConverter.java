package br.com.payment.webui.converters;

import br.com.payment.webui.dtos.PaymentOrderDTO;
import br.com.payment.webui.dtos.PaymentWebhookDTO;
import br.com.payment.webui.dtos.WebhookDataDTO;
import br.com.payment.webui.dtos.request.PaymentOrderRequest;
import br.com.payment.webui.dtos.request.PaymentWebhookRequest;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PaymentConverter {

  public static PaymentOrderDTO toPaymentOrderDTO(PaymentOrderRequest request) {
    return PaymentOrderDTO.builder()
      .paymentMethodId(request.getPaymentMethodId())
      .orderId(request.getOrderId())
      .build();
  }

  public static PaymentWebhookDTO paymentWebhookDTO(PaymentWebhookRequest webhookRequest) {
    return PaymentWebhookDTO.builder()
      .id(webhookRequest.getId())
      .liveMode(webhookRequest.getLiveMode())
      .type(webhookRequest.getType())
      .createDate(webhookRequest.getCreateDate())
      .userId(webhookRequest.getUserId())
      .apiVersion(webhookRequest.getApiVersion())
      .action(webhookRequest.getAction())
      .data(WebhookDataDTO.builder()
        .id(webhookRequest.getData().getId())
        .build())
      .build();
  }

}
