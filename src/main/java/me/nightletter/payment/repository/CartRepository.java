package me.nightletter.payment.repository;

import java.util.List;
import me.nightletter.payment.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query( "select c from Cart c inner join fetch c.product where c.user.userId =:userId" )
	List<Cart> findByUserId( Long userId );
}
