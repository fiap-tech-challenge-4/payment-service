package br.com.payment.app.integration.mercadopago.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateQRCodeRequest {

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount = ZERO;

    @JsonProperty("items")
    private List<ItemQRCodeRequest> items = new ArrayList<>();

}
