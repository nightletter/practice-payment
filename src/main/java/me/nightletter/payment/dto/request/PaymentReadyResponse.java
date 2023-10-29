package me.nightletter.payment.dto.request;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentReadyResponse implements Serializable {
	private String rescode;
	private String resmsg;
	private String fgkey;

	public PaymentReadyResponse( String rescode, String resmsg, String fgkey ) {
		this.rescode = rescode;
		this.resmsg = resmsg;
		this.fgkey = fgkey;
	}
}
