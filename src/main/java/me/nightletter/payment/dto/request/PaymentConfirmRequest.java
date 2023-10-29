package me.nightletter.payment.dto.request;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentConfirmRequest implements Serializable {
	private String currency;
	private String card_number1;
	private String transaction_date;
	private String card_number4;
	private String mid;
	private double amount;
	private String access_country;
	private String order_id;
	private String payment_method;
	private String email;
	private int ver;
	private String transaction_id;
	private String param3;
	private String resmsg;
	private String card_holder;
	private String rescode;
	private String auth_code;
	private String fgkey;
	private String transaction_type;
	private String pay_to;
}
