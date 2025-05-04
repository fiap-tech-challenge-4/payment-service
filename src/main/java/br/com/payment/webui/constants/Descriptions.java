package br.com.payment.webui.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Descriptions {

    public static final String HTTP_CODE = "Error http code";
    public static final String HTTP_DESCRIPTION = "Error http description";
    public static final String MESSAGE = "Error message";
    public static final String PATH = "Request path";
    public static final String TIMESTAMP = "Error timestamp";
    public static final String FIELDS = "List of fields with error";
    public static final String FIELD = "Field name with error";
    public static final String FIELD_MESSAGE = "Field error message";

    public static final String ID = "Unique identifier";
    public static final String PAGE = "Page number";
    public static final String LIMIT = "Number of records to retrieve per page";
    public static final String SORT = "Listing sort order";
    public static final String PAYMENT_METHOD_DESCRIPTION = "Payment method description";
    public static final String PAYMENT_METHOD_TYPE = "Payment method type";
    public static final String ORDER_ID = "Unique order identifier";
    public static final String PAYMENT_METHOD_ID = "Unique payment method identifier";
    public static final String QRCODE = "Payment QRCode";
    public static final String PAYMENT_IDENTIFIER = "Unique payment identifier";

    public static final String HAS_NEXT = "Indicates if there is a next page with elements";
    public static final String HAS_PREVIOUS = "Indicates if there is a previous page";
    public static final String PAGE_NUMBER = "Current page number";
    public static final String PAGE_SIZE = "Number of elements on the current page";
    public static final String ITEMS = "List of page items";
}
