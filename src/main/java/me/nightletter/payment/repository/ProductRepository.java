package me.nightletter.payment.repository;

import java.util.List;
import me.nightletter.payment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.productId in (:productIds)")
	List<Product> findProducts( List<Long> productIds );
}
