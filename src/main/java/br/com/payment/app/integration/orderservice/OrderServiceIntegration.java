package br.com.payment.app.integration.orderservice;

import br.com.payment.app.integration.orderservice.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service", url = "${integration.order-service.url}")
public interface OrderServiceIntegration {

    @GetMapping(value = "/api/v1/order/{id}", consumes = "application/json")
    OrderResponse getOrderById(@PathVariable("id") String orderId);

    @PostMapping(value = "/api/v1/order/status/{id}", consumes = "application/json")
    void notifyOrderStatusUpdate(@PathVariable("id") String orderId);
}
