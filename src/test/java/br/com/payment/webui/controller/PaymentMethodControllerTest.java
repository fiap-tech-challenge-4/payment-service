package br.com.payment.webui.controller;

import br.com.payment.app.usecases.PaymentMethodUseCase;
import br.com.payment.enums.PaymentType;
import br.com.payment.webui.controllers.PaymentMethodController;
import br.com.payment.webui.dtos.response.PaginationResponse;
import br.com.payment.webui.dtos.response.PaymentMethodResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentMethodController.class)
class PaymentMethodControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PaymentMethodUseCase paymentMethodUseCase;

  @Test
  void shouldReturnPaginatedListOfPaymentMethods() throws Exception {
    int page = 1;
    int limit = 25;
    String sort = "DESC";

    PaymentMethodResponse response = PaymentMethodResponse.builder()
      .id(UUID.randomUUID())
      .description("Credit Card")
      .paymentType(PaymentType.PIX)
      .build();

    PaginationResponse<PaymentMethodResponse> pagination = PaginationResponse.<PaymentMethodResponse>builder()
      .pageSize(1)
      .items(List.of(response))
      .build();

    when(paymentMethodUseCase.listPaymentMethods(page, limit, sort)).thenReturn(pagination);

    mockMvc.perform(get("/api/v1/method/payment/list")
        .param("page", String.valueOf(page))
        .param("limit", String.valueOf(limit))
        .param("sort", sort))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.pageSize", is(1)))
      .andExpect(jsonPath("$.items[0].paymentType", is("PIX")));
  }
}
