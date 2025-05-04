package br.com.payment.app.usecases;

import br.com.payment.app.repositories.PaymentMethodRepository;
import br.com.payment.webui.dtos.response.PaginationResponse;
import br.com.payment.webui.dtos.response.PaymentMethodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.payment.utils.Pagination.getPageRequest;
import static br.com.payment.webui.converters.PaymentMethodConverter.toPaymentMethodList;

@Service
@RequiredArgsConstructor
public class PaymentMethodUseCase {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaginationResponse<PaymentMethodResponse> listPaymentMethods(Integer page, Integer limit, String sort) {
        var pageable = getPageRequest(limit, page, sort, "id");
        return toPaymentMethodList(paymentMethodRepository.findAll(pageable));
    }
}
