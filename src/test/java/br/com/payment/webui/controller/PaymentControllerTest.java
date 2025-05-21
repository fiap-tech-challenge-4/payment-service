package br.com.payment.webui.controller;

import br.com.payment.app.usecases.PaymentOrderUseCase;
import br.com.payment.webui.controllers.PaymentController;
import br.com.payment.webui.dtos.request.PaymentOrderRequest;
import br.com.payment.webui.dtos.request.PaymentWebhookRequest;
import br.com.payment.webui.dtos.request.WebhookDataRequest;
import br.com.payment.webui.dtos.response.PaymentOrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PaymentOrderUseCase paymentOrderUseCase;

  @Autowired
  private ObjectMapper objectMapper;

  private PaymentOrderRequest paymentOrderRequest;
  private PaymentOrderResponse paymentOrderResponse;

  @BeforeEach
  void setup() {
    paymentOrderRequest = PaymentOrderRequest.builder()
      .orderId("order-id")
      .paymentMethodId(UUID.randomUUID().toString())
      .build();

    paymentOrderResponse = PaymentOrderResponse.builder()
      .qrcode("qrCodeData")
      .paymentId("payment-id")
      .paymentIdentifierExternal("payment-id-external")
      .build();
  }

  @Test
  void shouldReturnCreatedWhenPaymentRequestIsValid() throws Exception {
    when(paymentOrderUseCase.paymentOrder(any())).thenReturn(paymentOrderResponse);

    mockMvc.perform(post("/api/v1/payment")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(paymentOrderRequest)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.qrcode").value("qrCodeData"))
      .andExpect(jsonPath("$.paymentId").value("payment-id"))
      .andExpect(jsonPath("$.paymentIdentifierExternal").value("payment-id-external"));
  }

  @Test
  void shouldReturnOkWhenPaymentWebhookIsValid() throws Exception {
    PaymentWebhookRequest webhookRequest = PaymentWebhookRequest.builder()
      .id("order-id")
      .createDate("date")
      .data(WebhookDataRequest.builder()
        .id("payment-id")
        .build())
      .build();

    mockMvc.perform(post("/api/v1/payment/webhook")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(webhookRequest)))
      .andExpect(status().isOk());

    verify(paymentOrderUseCase, times(1)).paymentWebhook(any());
  }
}
