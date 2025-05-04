package br.com.payment.webui.converters;

import br.com.payment.app.entities.PaymentMethod;
import br.com.payment.webui.dtos.response.PaginationResponse;
import br.com.payment.webui.dtos.response.PaymentMethodResponse;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import static br.com.payment.enums.PaymentType.valueOf;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PaymentMethodConverter {

  public static PaginationResponse<PaymentMethodResponse> toPaymentMethodList(Page<PaymentMethod> page) {
    return PaginationResponse.<PaymentMethodResponse>builder()
      .hasNext(!page.isLast())
      .hasPrevious(!page.isFirst())
      .pageNumber(page.getNumber() + 1)
      .pageSize(page.getSize())
      .items(page.stream()
        .map(PaymentMethodConverter::toPaymentMethodResponse)
        .toList())
      .build();
  }

  public static PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod) {
    return PaymentMethodResponse.builder()
      .id(paymentMethod.getId())
      .description(paymentMethod.getDescription())
      .paymentType(valueOf(paymentMethod.getPaymentType()))
      .build();
  }

}
