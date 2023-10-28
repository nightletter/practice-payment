package me.nightletter.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table( name = "t_payment" )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Entity
public class Payment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "payment_id" )
	private Long paymentId;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "order_id" )
	private Order order;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "product_id" )
	private Product product;

	@Enumerated( EnumType.STRING )
	private PaymentStatus paymentStatus;
	private Integer saleQuantity;
	private Integer price;

	@Builder
	public Payment( Long paymentId, Order order, Product product, PaymentStatus paymentStatus, Integer saleQuantity, Integer price ) {
		this.paymentId = paymentId;
		this.order = order;
		this.product = product;
		this.paymentStatus = paymentStatus;
		this.saleQuantity = saleQuantity;
		this.price = price;
	}

	public static Payment create(Order order, Product product, Integer saleQuantity, Integer price) {
		return Payment.builder()
			.order(order)
			.product(product)
			.paymentStatus(PaymentStatus.PEND)
			.saleQuantity( saleQuantity )
			.price( price )
			.build();
	}
}
