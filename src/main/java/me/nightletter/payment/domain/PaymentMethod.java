package me.nightletter.payment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
	P000("신용카드"),
	P110("BC카드"),
	P111("KB카드"),
	P112("하나카드(구 외환)"),
	P113("삼성카드"),
	P114("신한카드"),
	P115("현대카드"),
	P116("롯데카드"),
	P117("농협카드"),
	P119("씨티카드"),
	P120("우리카드"),
	P121("수협카드"),
	P122("제주카드"),
	P123("전북카드"),
	P124("광주카드"),
	P125("카카오뱅크"),
	P126("케이뱅크"),
	P127("미래에셋대우"),
	P128("코나카드"),
	P129("토스카드"),
	P130("차이카드"),
	P301("뱅크페이"),
	P302("카카오페이"),
	P303("토스"),
	P304("페이코"),
	P305("가상계좌"),
	P306("스마일페이"),
	P015("네이버페이(카드&포인트)"),
	P307("네이버페이(카드)"),
	P308("네이버페이(포인트)");

	private final String desc;
}
