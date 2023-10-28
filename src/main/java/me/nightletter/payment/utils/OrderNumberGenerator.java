package me.nightletter.payment.utils;

public interface OrderNumberGenerator {

	String generate(Long userId, long millis);
}
