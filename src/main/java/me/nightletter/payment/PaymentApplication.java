package me.nightletter.payment;

import jakarta.annotation.PostConstruct;
import me.nightletter.payment.domain.Cart;
import me.nightletter.payment.domain.Product;
import me.nightletter.payment.domain.User;
import me.nightletter.payment.repository.CartRepository;
import me.nightletter.payment.repository.ProductRepository;
import me.nightletter.payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentApplication {

	public static void main( String[] args ) {
		SpringApplication.run( PaymentApplication.class, args );
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartRepository cartRepository;

	@PostConstruct
	public void inti() {
		User savedUser = userRepository.save( new User( 1L, "테스트", "test@test.com" ) );
		Product savedProductA = productRepository.save( new Product( 1L, "바닐라라떼", 5000, 100 ) );
		Product savedProductB = productRepository.save( new Product( 2L, "돌체라떼", 5500, 100 ) );

		cartRepository.save( new Cart( savedUser, savedProductA, 1 ) );
		cartRepository.save( new Cart( savedUser, savedProductB, 1 ) );
	}
}
