package br.com.payment.webui.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookRequest {

    private String id;

    @JsonProperty("live_mode")
    private Boolean liveMode;

    private String type;

    @JsonProperty("date_created")
    private String createDate;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("api_version")
    private String apiVersion;

    private String action;

    private WebhookDataRequest data;

}
