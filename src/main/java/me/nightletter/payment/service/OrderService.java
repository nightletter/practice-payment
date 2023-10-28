package me.nightletter.payment.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import me.nightletter.payment.domain.Cart;
import me.nightletter.payment.domain.Order;
import me.nightletter.payment.domain.Payment;
import me.nightletter.payment.domain.User;
import me.nightletter.payment.dto.request.OrderRequest;
import me.nightletter.payment.dto.request.PaymentResponse;
import me.nightletter.payment.exception.ResourceNotFountException;
import me.nightletter.payment.repository.CartRepository;
import me.nightletter.payment.repository.OrderRepository;
import me.nightletter.payment.repository.PaymentRepository;
import me.nightletter.payment.repository.UserRepository;
import me.nightletter.payment.utils.OrderNumberGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final PaymentRepository paymentRepository;
	private final OrderNumberGenerator orderNumberGenerator;
	private final PaymentApiTemplate paymentApiTemplate;

	@Value( "${payment.mid}" )
	private String mid;
	@Value( "${host.url}" )
	private String hostUrl;

	public PaymentResponse create( OrderRequest orderRequest ) {

		User findBuyer = userRepository.findById( orderRequest.getUserId() )
			.orElseThrow( () -> new ResourceNotFountException( "user", orderRequest.getUserId() ) );

		List<Cart> findCarts = cartRepository.findByUserId( findBuyer.getUserId() );
		cartValidation( findCarts );

		int totalPrice = findCarts.stream()
			.mapToInt( cart -> cart.getProduct().getPrice() )
			.sum();

		Order savedOrder = orderRepository.save( new Order( orderNumberGenerator.generate( findBuyer.getUserId(), System.currentTimeMillis()), orderRequest.getPaymentMethod(), findBuyer, totalPrice ) );
		findCarts.forEach( cart -> {
			cart.getProduct().decrementStockQuantity( cart.getSaleQuantity() );
			paymentRepository.save( Payment.create( savedOrder, cart.getProduct(), cart.getSaleQuantity(), cart.getProduct().getPrice() ) );
		} );

		PaymentResponse result = new PaymentResponse( mid, savedOrder, findBuyer, hostUrl );

		Map map = paymentApiTemplate.executeReady( result );
		return null;
	}

	private void cartValidation( List<Cart> carts ) {
		if (carts.isEmpty()) {
			throw new ResourceNotFountException();
		}
	}
}
