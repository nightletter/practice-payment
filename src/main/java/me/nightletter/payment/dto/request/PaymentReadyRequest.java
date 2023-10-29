package me.nightletter.payment.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.nightletter.payment.domain.Order;
import me.nightletter.payment.domain.User;

@Getter
public class PaymentReadyRequest implements Serializable {

	private Payment payment;
	private Merchant merchant;
	private Buyer buyer;
	private Url url;
	private Settings settings;
	private List<Product> product = new ArrayList<>();
	@Setter
	private String fgkey;

	public PaymentReadyRequest( String mid, Order order, List<me.nightletter.payment.domain.Payment> payments, User user, String hostUrl ) {
		this.payment = new Payment( order );
		this.merchant = new Merchant( mid );
		this.buyer = new Buyer( user.getName(), user.getEmail() );
		this.url = new Url( hostUrl );
		this.settings = new Settings();

		payments.forEach( payment -> {
			product.add( new Product( payment.getProduct().getName(), payment.getSaleQuantity().toString(), payment.getPrice().toString()) );
		} );
	}

	@Getter
	public static class Payment implements Serializable {

		private String transaction_type;
		private String order_id;
		private String currency;
		private String amount;
		private String lang;
		private String payment_method;

		public Payment( Order order ) {
			this.transaction_type = "PAYMENT";
			this.order_id = order.getOrderNumber();
			this.currency = "KRW";
			this.amount = order.getTotalPrice().toString();
			this.lang = "KR";
			this.payment_method = order.getPaymentMethod().name();
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Merchant implements Serializable {

		private String mid;
	}

	@Getter
	@AllArgsConstructor
	public static class Buyer implements Serializable {

		private String name;
		private String email;
	}

	@Getter
	public static class Url implements Serializable {

		private String return_url;
		private String status_url;

		public Url( String hostUrl ) {
			this.return_url = hostUrl + "/api/order/return";
			this.status_url = hostUrl + "/api/order/confirm";
		}
	}

	@Getter
	private static class Settings implements Serializable {

		private String issuer_country = "KR";
		private String autoclose = "Y";
	}

	@Getter
	private static class Product implements Serializable {

		private String name;
		private String quantity;
		private String unit_price;
		private String link;

		public Product( String name, String quantity, String unit_price ) {
			this.name = name;
			this.quantity = quantity;
			this.unit_price = unit_price;
			this.link = "http://localhost:8080";
		}
	}
}
