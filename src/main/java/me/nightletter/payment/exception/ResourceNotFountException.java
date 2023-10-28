package me.nightletter.payment.exception;

import static me.nightletter.payment.ExceptionEnums.RESOURCE_NOT_FOUND;

import me.nightletter.payment.ExceptionEnums;

public class ResourceNotFountException extends RuntimeException {

	public ResourceNotFountException() {
		super ( RESOURCE_NOT_FOUND.getMessage());
	}

	public ResourceNotFountException( String target, Long targetId ) {
		super( RESOURCE_NOT_FOUND.getMessage() + " " + target + " = " + targetId );
	}
}
