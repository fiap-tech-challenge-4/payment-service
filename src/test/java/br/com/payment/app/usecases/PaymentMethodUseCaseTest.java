package br.com.payment.app.usecases;

import br.com.payment.app.entities.PaymentMethod;
import br.com.payment.app.repositories.PaymentMethodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static br.com.payment.enums.PaymentType.PIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentMethodUseCaseTest {

  @Mock
  private PaymentMethodRepository paymentMethodRepository;

  @InjectMocks
  private PaymentMethodUseCase useCase;

  @Test
  void shouldReturnPaginatedPaymentMethods() {
    var method = PaymentMethod.builder()
      .id(UUID.randomUUID())
      .description("Pix")
      .paymentType(PIX.name())
      .build();

    Page<PaymentMethod> page = new PageImpl<>(List.of(method));
    when(paymentMethodRepository.findAll(any(Pageable.class))).thenReturn(page);

    var response = useCase.listPaymentMethods(0, 10, "DESC");

    assertNotNull(response);
    assertEquals(1, response.getItems().size());
    assertEquals("Pix", response.getItems().getFirst().getDescription());
    verify(paymentMethodRepository, times(1)).findAll(any(Pageable.class));
  }
}
