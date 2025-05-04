package br.com.payment.app.integration.orderservice.response;

import br.com.payment.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static br.com.payment.webui.constants.Constants.DATE_TIME_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT, timezone = "UTC")
    private LocalDateTime date;

    private String clientId;

    private BigDecimal totalValue;
    private OrderStatus status;
    private List<OrderItemResponse> items;
    private String additionalInfo;

}
