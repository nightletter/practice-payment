package me.nightletter.payment.exception;

import static me.nightletter.payment.ExceptionEnums.RESOURCE_NOT_FOUND;

public class ResourceNotFountException extends RuntimeException {

	public ResourceNotFountException() {
		super ( RESOURCE_NOT_FOUND.getMessage());
	}

	public ResourceNotFountException( String targetObject, Long targetId ) {
		super( RESOURCE_NOT_FOUND.getMessage() + " " + targetObject + " = " + targetId );
	}

	public ResourceNotFountException( String targetObject, String target ) {
		super( RESOURCE_NOT_FOUND.getMessage() + " " + targetObject + " = " + target );
	}
}
