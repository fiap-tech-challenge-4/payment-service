package br.com.payment.app.integration.mercadopago.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageResponse {

    private String error;
    private String message;
    private String status;

}
