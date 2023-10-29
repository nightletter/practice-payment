package me.nightletter.payment.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.nightletter.payment.domain.Cart;
import me.nightletter.payment.domain.Order;
import me.nightletter.payment.domain.Payment;
import me.nightletter.payment.domain.PaymentStatus;
import me.nightletter.payment.domain.User;
import me.nightletter.payment.dto.request.OrderRequest;
import me.nightletter.payment.dto.request.PaymentConfirmRequest;
import me.nightletter.payment.dto.request.PaymentReadyRequest;
import me.nightletter.payment.dto.request.PaymentReadyResponse;
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

	public PaymentReadyRequest create( OrderRequest orderRequest ) {

		User findBuyer = userRepository.findById( orderRequest.getUserId() )
			.orElseThrow( () -> new ResourceNotFountException( "user", orderRequest.getUserId() ) );

		List<Cart> findCarts = cartRepository.findByUserId( findBuyer.getUserId() );
		cartValidation( findCarts );

		int totalPrice = findCarts.stream()
			.mapToInt( cart -> cart.getProduct().getPrice() )
			.sum();

		Order builtOrder = Order.builder()
			.orderNumber( orderNumberGenerator.generate( findBuyer.getUserId(), System.currentTimeMillis() ) )
			.paymentMethod( orderRequest.getPaymentMethod() )
			.user( findBuyer )
			.totalPrice( totalPrice )
			.build();

		Order savedOrder = orderRepository.save( builtOrder );
		List<Payment> savedPayments = new ArrayList<>();

		findCarts.forEach( cart -> {
			cart.getProduct().decrementStockQuantity( cart.getSaleQuantity() );
			Payment savedPayment = paymentRepository.save( Payment.create( savedOrder, cart.getProduct(), cart.getSaleQuantity(), cart.getProduct().getPrice() ) );
			savedPayments.add( savedPayment );
		} );

		PaymentReadyRequest result = new PaymentReadyRequest( mid, savedOrder, savedPayments, findBuyer, hostUrl );
		PaymentReadyResponse readyResult = paymentApiTemplate.executeReady( result );

		result.setFgkey( readyResult.getFgkey() );

		return result;
	}

	@Transactional
	public void confirm( PaymentConfirmRequest request ) {
		Order findOrder = orderRepository.findByOrderNumber( request.getOrder_id() )
			.orElseThrow( () -> new ResourceNotFountException( "order", request.getOrder_id() ) );

		List<Payment> findPayments = paymentRepository.findByOrderId( findOrder.getOrderId() );

		findOrder.confirm( request.getTransaction_id() );
		findPayments.forEach( payment -> {
			payment.confirm();
		} );
	}

	private void cartValidation( List<Cart> carts ) {
		if (carts.isEmpty()) {
			throw new ResourceNotFountException();
		}
	}

	public String verify( String orderNumber ) {
		List<Payment> findPayments = paymentRepository.findByOrderNumber( orderNumber );

		for ( Payment payment : findPayments ) {
			if (payment.getPaymentStatus() == PaymentStatus.PEND) {
				return PaymentStatus.PEND.name();
			}

			if (payment.getPaymentStatus() == PaymentStatus.SALE) {
				return PaymentStatus.SALE.name();
			}

			if (payment.getPaymentStatus() == PaymentStatus.FAIL) {
				return PaymentStatus.FAIL.name();
			}
		}

		return PaymentStatus.FAIL.name();
	}
}
