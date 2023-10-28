package me.nightletter.payment.dto.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.nightletter.payment.domain.PaymentMethod;

@Getter
@AllArgsConstructor
public class OrderRequest implements Serializable {

	private Long userId;
	private PaymentMethod paymentMethod;
}
