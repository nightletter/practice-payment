package me.nightletter.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

@Getter
@RequiredArgsConstructor
public enum ExceptionEnums {

	RESOURCE_NOT_FOUND("Not Found");

	private final String message;
}
