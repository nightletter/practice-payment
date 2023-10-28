package me.nightletter.payment.stub;

import lombok.RequiredArgsConstructor;
import me.nightletter.payment.utils.OrderNumberGenerator;

@RequiredArgsConstructor
public class StubOrderNumberGenerator implements OrderNumberGenerator {

	private final Long millis;

	@Override
	public String generate( Long userId, long millis ) {
		return userId + "_" + this.millis;
	}
}
