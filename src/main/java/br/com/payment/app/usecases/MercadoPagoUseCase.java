package br.com.payment.app.usecases;

import br.com.payment.app.integration.mercadopago.MercadoPagoIntegration;
import br.com.payment.app.integration.mercadopago.request.GenerateQRCodeRequest;
import br.com.payment.app.integration.mercadopago.request.ItemQRCodeRequest;
import br.com.payment.app.integration.orderservice.response.OrderResponse;
import br.com.payment.webui.dtos.response.PaymentOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static br.com.payment.enums.UnitType.UNIT;

@Service
@RequiredArgsConstructor
public class MercadoPagoUseCase {

  private final MercadoPagoIntegration mercadoPagoIntegration;

  @Value("${mercado-pago.user-id}")
  private String userId;

  @Value("${mercado-pago.external-pos-id}")
  private String externalPosId;

  public PaymentOrderResponse pixPaymentOrder(OrderResponse order) {
    var response = mercadoPagoIntegration.generateQrCode(userId, externalPosId, getRequestQRCode(order));
    return PaymentOrderResponse.builder()
      .qrcode(response.getQrcode())
      .paymentIdentifier(response.getExternalOrderId())
      .build();
  }

  private GenerateQRCodeRequest getRequestQRCode(OrderResponse order) {
    List<ItemQRCodeRequest> itemsList = new ArrayList<>();
    order.getItems().forEach(item -> {
      ItemQRCodeRequest itemReq = ItemQRCodeRequest.builder()
        .itemDescription(item.getDescription())
        .unitPrice(item.getValue())
        .quantity(item.getQuantity())
        .unitDescription(UNIT)
        .totalAmount(item.getValue().multiply(BigDecimal.valueOf(item.getQuantity())))
        .build();
      itemsList.add(itemReq);
    });

    return GenerateQRCodeRequest.builder()
      .externalReference(String.valueOf(order.getId()))
      .title(String.format("Order %s", order.getId()))
      .description(String.format("Order %s", order.getId()))
      .totalAmount(order.getTotalValue())
      .items(itemsList)
      .build();
  }
}
