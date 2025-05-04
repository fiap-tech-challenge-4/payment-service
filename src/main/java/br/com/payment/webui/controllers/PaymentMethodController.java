package br.com.payment.webui.controllers;

import br.com.payment.app.usecases.PaymentMethodUseCase;
import br.com.payment.webui.dtos.response.PaginationResponse;
import br.com.payment.webui.dtos.response.PaymentMethodResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.payment.errors.Errors.MINIMAL_PAGE;
import static br.com.payment.webui.constants.Descriptions.LIMIT;
import static br.com.payment.webui.constants.Descriptions.PAGE;
import static br.com.payment.webui.constants.Descriptions.SORT;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Payment Method")
@ApiResponse(responseCode = "400", description = "Bad Request",
  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/method/payment")
public class PaymentMethodController {

  private static final String ASC_DESC = "ASC/DESC";

  private final PaymentMethodUseCase paymentMethodUseCase;

  @Operation(summary = "Payment Method List")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Payment Method List retrieved successfully",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaymentMethodResponse.class))})})
  @GetMapping("/list")
  public ResponseEntity<PaginationResponse<PaymentMethodResponse>> listPaymentMethods(
    @Parameter(description = PAGE)
    @RequestParam(required = false, defaultValue = "1")
    @Min(value = 1, message = MINIMAL_PAGE)
    final Integer page,
    @Parameter(description = LIMIT)
    @RequestParam(required = false, defaultValue = "25")
    final Integer limit,
    @Parameter(description = SORT, example = ASC_DESC)
    @RequestParam(required = false, defaultValue = "DESC")
    final String sort) {
    return ResponseEntity.ok(paymentMethodUseCase.listPaymentMethods(page, limit, sort));
  }
}
