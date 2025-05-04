package br.com.payment.webui.controllers;

import br.com.payment.app.usecases.PaymentOrderUseCase;
import br.com.payment.webui.dtos.request.PaymentOrderRequest;
import br.com.payment.webui.dtos.request.PaymentWebhookRequest;
import br.com.payment.webui.dtos.response.PaymentOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.payment.webui.converters.PaymentConverter.paymentWebhookDTO;
import static br.com.payment.webui.converters.PaymentConverter.toPaymentOrderDTO;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Payment")
@ApiResponse(responseCode = "400", description = "Bad Request",
  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/payment")
public class PaymentController {

  private final PaymentOrderUseCase paymentOrderUseCase;

  @Operation(summary = "Payment request")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Payment request created successfully",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaymentOrderResponse.class))})})
  @PostMapping
  public ResponseEntity<PaymentOrderResponse> paymentRequest(
    @RequestBody
    @Valid PaymentOrderRequest request) {
    var response = paymentOrderUseCase.paymentOrder(toPaymentOrderDTO(request));
    return ResponseEntity.status(CREATED)
      .body(response);
  }

  @Operation(summary = "Payment webhook notification")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Webhook notification received successfully")})
  @PostMapping("/webhook")
  public ResponseEntity<Void> paymentWebhook(
    @RequestBody @Valid PaymentWebhookRequest request) {
    paymentOrderUseCase.paymentWebhook(paymentWebhookDTO(request));
    return ResponseEntity.status(OK).build();
  }

}
