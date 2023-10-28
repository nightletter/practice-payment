package me.nightletter.payment;

import lombok.RequiredArgsConstructor;
import me.nightletter.payment.domain.PaymentMethod;
import me.nightletter.payment.dto.request.OrderRequest;
import me.nightletter.payment.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {

	private final OrderService orderService;

	@PostMapping("/create")
	public ResponseEntity create() {
		orderService.create( new OrderRequest( 1L, PaymentMethod.P000 ) );
		return ResponseEntity.ok()
			.build();
	}
}
