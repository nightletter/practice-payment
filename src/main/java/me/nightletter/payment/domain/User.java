package me.nightletter.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table( name = "t_user" )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	private String name;

	private String email;

	public User( Long userId, String name, String email ) {
		this.userId = userId;
		this.name = name;
		this.email = email;
	}
}
