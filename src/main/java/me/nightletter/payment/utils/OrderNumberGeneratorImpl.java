package me.nightletter.payment.utils;

import org.springframework.stereotype.Component;

@Component
public class OrderNumberGeneratorImpl implements OrderNumberGenerator{

	@Override
	public String generate( Long userId, long millis ) {
		return userId + "_" + millis;
	}
}
