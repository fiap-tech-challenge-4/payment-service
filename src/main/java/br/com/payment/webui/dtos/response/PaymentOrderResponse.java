package br.com.payment.webui.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.payment.webui.constants.Descriptions.PAYMENT_IDENTIFIER;
import static br.com.payment.webui.constants.Descriptions.QRCODE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderResponse {

    @Schema(description = QRCODE)
    private String qrcode;

    @Schema(description = PAYMENT_IDENTIFIER)
    private String paymentIdentifier;
}