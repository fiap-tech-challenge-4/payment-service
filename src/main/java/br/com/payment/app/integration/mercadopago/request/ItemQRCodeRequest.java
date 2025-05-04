package br.com.payment.app.integration.mercadopago.request;

import br.com.payment.enums.UnitType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemQRCodeRequest {

    @JsonProperty("title")
    private String itemDescription;

    @JsonProperty("unit_price")
    private BigDecimal unitPrice;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("unit_measure")
    private UnitType unitDescription;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
}