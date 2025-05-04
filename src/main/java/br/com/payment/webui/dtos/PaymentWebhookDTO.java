package br.com.payment.webui.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookDTO {

    private String id;
    private Boolean liveMode;
    private String type;
    private String createDate;
    private Long userId;
    private String apiVersion;
    private String action;
    private WebhookDataDTO data;

}
