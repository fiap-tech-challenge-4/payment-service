package br.com.payment.webui.dtos.response;

import br.com.payment.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static br.com.payment.webui.constants.Descriptions.PAYMENT_METHOD_DESCRIPTION;
import static br.com.payment.webui.constants.Descriptions.ID;
import static br.com.payment.webui.constants.Descriptions.PAYMENT_METHOD_TYPE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PaymentMethodResponse {

    @Schema(description = ID)
    private UUID id;

    @Schema(description = PAYMENT_METHOD_DESCRIPTION)
    private String description;

    @Schema(description = PAYMENT_METHOD_TYPE)
    private PaymentType paymentType;

}
