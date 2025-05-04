package br.com.payment.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.payment.errors.Errors.REQUIRED_FIELD;
import static br.com.payment.webui.constants.Descriptions.PAYMENT_METHOD_ID;
import static br.com.payment.webui.constants.Descriptions.ORDER_ID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderRequest {

    @Schema(description = PAYMENT_METHOD_ID)
    @NotBlank(message = REQUIRED_FIELD)
    private String paymentMethodId;

    @Schema(description = ORDER_ID)
    @NotBlank(message = REQUIRED_FIELD)
    private String orderId;
}
