package me.nightletter.payment.dto.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.nightletter.payment.domain.Order;
import me.nightletter.payment.domain.User;

@Getter
public class PaymentResponse implements Serializable {
	private Payment payment;
	private Merchant merchant;
	private Buyer buyer;
	private Url url;

	public PaymentResponse( String mid, Order order, User user, String hostUrl) {
		this.payment = new Payment( order );
		this.merchant = new Merchant( mid );
		this.buyer = new Buyer( user.getName(), user.getEmail());
		this.url = new Url( hostUrl );
	}

	@Getter
	public static class Payment implements Serializable{
		private String transaction_type;
		private String order_id;
		private String currency;
		private String amount;
		private String lang;

		public Payment( Order order ) {
			this.transaction_type = "PAYMENT";
			this.order_id = order.getOrderNumber();
			this.currency = "KRW";
			this.amount = order.getTotalPrice().toString();
			this.lang = "KR";
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Merchant implements Serializable{
		private String mid;
	}

	@Getter
	@AllArgsConstructor
	public static class Buyer implements Serializable {
		private String name;
		private String email;
	}

	@Getter
	public static class Url implements Serializable{
		private String return_url;
		private String status_url;

		public Url( String hostUrl) {
			this.return_url = hostUrl + "/api/order/return";
			this.status_url = hostUrl + "/api/order/status";
		}
	}
}
