package me.nightletter.payment.repository;

import java.util.List;
import me.nightletter.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	@Query("select p from Payment p where p.order.orderId =:orderId")
	List<Payment> findByOrderId(Long orderId);

}
