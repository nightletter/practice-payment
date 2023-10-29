package me.nightletter.payment;

import static org.springframework.http.ResponseEntity.ok;

import lombok.RequiredArgsConstructor;
import me.nightletter.payment.domain.PaymentMethod;
import me.nightletter.payment.dto.request.OrderRequest;
import me.nightletter.payment.dto.request.PaymentConfirmRequest;
import me.nightletter.payment.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/order" )
@RequiredArgsConstructor
public class OrderRestController {

	private final OrderService orderService;

	@PostMapping( "/create" )
	public ResponseEntity orderCreate() {
		return ok( orderService.create( new OrderRequest( 1L, PaymentMethod.P304 ) ) );
	}

	@PostMapping( "/confirm" )
	public void orderConfirm( @ModelAttribute PaymentConfirmRequest request ) {
		orderService.confirm( request );
	}

	@PostMapping("/return")
	public ResponseEntity orderReturn() {
		return ResponseEntity.ok().build();
	}

	@GetMapping( "/{orderNumber}" )
	public ResponseEntity orderVerify( @PathVariable String orderNumber ) {
		return ok( orderService.verify( orderNumber ) );
	}
}
