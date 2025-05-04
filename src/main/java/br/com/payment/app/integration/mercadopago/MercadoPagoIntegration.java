package br.com.payment.app.integration.mercadopago;

import br.com.payment.app.integration.mercadopago.request.GenerateQRCodeRequest;
import br.com.payment.app.integration.mercadopago.response.GenerateQRCodeResponse;
import br.com.payment.config.MercadoPagoIntegrationConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mercado-pago", url = "${integration.mercado-pago.url}", configuration = MercadoPagoIntegrationConfig.class)
public interface MercadoPagoIntegration {

    @PostMapping(value = "/instore/orders/qr/seller/collectors/{userId}/pos/{externalPosId}/qrs", consumes = "application/json")
    GenerateQRCodeResponse generateQrCode(@PathVariable("userId") String userId,
                                          @PathVariable("externalPosId") String externalPosId,
                                          GenerateQRCodeRequest request);
}
