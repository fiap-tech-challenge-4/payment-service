package br.com.payment.webui.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderDTO {

    private String paymentMethodId;
    private String orderId;

}
