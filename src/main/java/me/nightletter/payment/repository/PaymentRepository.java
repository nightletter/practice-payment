package me.nightletter.payment.repository;

import java.util.List;
import me.nightletter.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	@Query("select p from Payment p where p.order.orderId =:orderId")
	List<Payment> findByOrderId(Long orderId);

	@Query("select p from Payment p inner join p.order o where o.orderNumber =:orderNumber")
	List<Payment> findByOrderNumber(String orderNumber);

	@Query("select p from Payment p join fetch p.order o where o.orderNumber =:orderNumber")
	List<Payment> findByOrderNumberFetchOrder(String orderNumber);
}
