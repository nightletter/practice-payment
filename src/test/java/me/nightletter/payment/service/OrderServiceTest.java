package me.nightletter.payment.service;

import static me.nightletter.payment.domain.PaymentMethod.P000;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import me.nightletter.payment.domain.Cart;
import me.nightletter.payment.domain.Order;
import me.nightletter.payment.domain.Payment;
import me.nightletter.payment.domain.PaymentStatus;
import me.nightletter.payment.domain.Product;
import me.nightletter.payment.domain.User;
import me.nightletter.payment.dto.request.OrderRequest;
import me.nightletter.payment.exception.ResourceNotFountException;
import me.nightletter.payment.repository.CartRepository;
import me.nightletter.payment.repository.OrderRepository;
import me.nightletter.payment.repository.PaymentRepository;
import me.nightletter.payment.repository.ProductRepository;
import me.nightletter.payment.repository.UserRepository;
import me.nightletter.payment.stub.StubOrderNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

	@Autowired
	UserRepository userRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	ProductRepository productRepository;

	OrderService orderService;

	private final Long USER_ID = 1L;
	private final Long PRODUCT_1 = 1L;
	private final Long PRODUCT_2 = 2L;


	private final long ORDER_NUMBER_MILLIS = 12314121L;

	@BeforeEach
	void before() {
		this.orderService = new OrderService(
			this.userRepository,
			this.orderRepository,
			this.cartRepository,
			this.paymentRepository,
			new StubOrderNumberGenerator( ORDER_NUMBER_MILLIS )
		);

		userRepository.save( new User( USER_ID, "테스트", "test@test.com" ) );
		productRepository.save( new Product( PRODUCT_1, "바닐라라떼", 5000, 100 ) );
		productRepository.save( new Product( PRODUCT_2, "돌체라떼", 5500, 100 ) );
	}

	@Test
	void createOrder() {
		//		given
		User findUser = userRepository.findById( USER_ID ).get();
		Product findProduct = productRepository.findById( PRODUCT_1 ).get();
		cartRepository.save( new Cart( findUser, findProduct, 1 ) );

		//		when
		OrderRequest orderRequest = new OrderRequest( findUser.getUserId(), P000 );
		orderService.create( orderRequest );

		//		then
		Order findOrder = orderRepository.findByUserId( USER_ID ).get();
		List<Payment> findPayments = paymentRepository.findByOrderId( findOrder.getOrderId() );

		assertThat( findOrder.getOrderId() ).isNotNull();
		assertThat( findOrder.getUser().getUserId() ).isEqualTo( orderRequest.getUserId() );
		assertThat( findOrder.getOrderNumber() ).isEqualTo( orderRequest.getUserId() + "_" + ORDER_NUMBER_MILLIS );
		assertThat( findOrder.getPaymentMethod() ).isEqualTo( orderRequest.getPaymentMethod() );
		assertThat( findOrder.getTotalPrice() ).isEqualTo(
			findPayments.stream()
				.mapToInt( payment -> payment.getPrice() )
				.sum()
		);

		assertThat( findPayments.size() ).isEqualTo( 1 );
		assertThat( findPayments.get( 0 ).getProduct().getProductId() ).isEqualTo( PRODUCT_1 );
		assertThat( findPayments.get( 0 ).getSaleQuantity() ).isEqualTo( 1 );
		assertThat( findPayments.get( 0 ).getPaymentStatus() ).isEqualTo( PaymentStatus.PEND );
	}

	@Test
	void emptyCart() {
		assertThatThrownBy( () -> orderService.create( new OrderRequest( USER_ID, P000 ) ) )
			.isInstanceOf( ResourceNotFountException.class );
	}
}