package me.nightletter.payment.domain;

import lombok.Getter;

@Getter
public enum PaymentStatus {

	PEND,SALE, REFUND, FAIL;
}
