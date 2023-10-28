package me.nightletter.payment.repository;

import me.nightletter.payment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
