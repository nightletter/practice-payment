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

@Table(name = "t_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	private String orderNumber;

	@Enumerated(value = EnumType.STRING)
	private PaymentMethod paymentMethod;
	private String transactionId;
	private Integer totalPrice;

	@Builder
	public Order( String orderNumber, PaymentMethod paymentMethod, User user, int totalPrice ) {
		this.orderNumber = orderNumber;
		this.paymentMethod = paymentMethod;
		this.user = user;
		this.totalPrice = totalPrice;
	}

	public void confirm(String transactionId) {
		this.transactionId = transactionId;
	}
}
