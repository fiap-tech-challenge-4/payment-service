package br.com.payment.errors;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Errors {

    public static final String REQUIRED_FIELD = "Required field";
    public static final String PAYMENT_METHOD_NOT_FOUND = "Payment method not found";
    public static final String PAYMENT_METHOD_NOT_AVAILABLE = "Payment method not available";
    public static final String ORDER_STATUS_IS_NOT_RECEIVED = "Order status is not received";
    public static final String PAYMENT_NOT_FOUND = "Payment not found";
    public static final String MINIMAL_PAGE = "Minimal page is 1";

}
