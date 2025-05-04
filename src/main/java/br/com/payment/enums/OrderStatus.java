package br.com.payment.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    RECEIVED,
    PREPARING,
    READY,
    COMPLETED

}
