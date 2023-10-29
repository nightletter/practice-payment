package me.nightletter.payment.repository;

import java.util.Optional;
import me.nightletter.payment.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("select o from Order o where o.user.userId =:userId")
	Optional<Order> findByUserId(Long userId);

	@Query("select o from Order o where o.orderNumber =:orderNumber")
	Optional<Order> findByOrderNumber(String orderNumber);
}
