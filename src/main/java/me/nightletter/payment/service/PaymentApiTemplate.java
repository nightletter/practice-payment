package me.nightletter.payment.service;

import static org.springframework.http.HttpMethod.POST;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import me.nightletter.payment.dto.request.PaymentReadyResponse;
import me.nightletter.payment.dto.request.PaymentReadyRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentApiTemplate {

	private String ready = "https://api-test.eximbay.com/v1/payments/ready";

	@Value( "${payment.access}" )
	private String accessKey;

	public PaymentReadyResponse executeReady( PaymentReadyRequest paymentReadyRequest ) {
		RestTemplate restTemplate = new RestTemplate();
		RequestEntity requestEntity = new RequestEntity<>( paymentReadyRequest, setEximbayRequestHeader(), POST, URI.create( ready ) );

		PaymentReadyResponse result = restTemplate.exchange( requestEntity, PaymentReadyResponse.class )
			.getBody();

		return result;
	}

	private HttpHeaders setEximbayRequestHeader() {
		//        키 맨끝 + : , 참고 : https://developer.eximbay.com/payment_linkage/preparing-payment.html
		String encodeTarget = accessKey + ":";
		String encodedApiKey = new String( Base64.getEncoder().encode( encodeTarget.getBytes() ) );

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth( encodedApiKey );
		httpHeaders.setContentType( MediaType.APPLICATION_JSON );

		return httpHeaders;
	}

	private String queryParamGenerator( Map<String, String> params ) {
		StringBuilder queryStringBuilder = new StringBuilder();
		for ( Map.Entry<String, String> entry : params.entrySet() ) {
			if ( queryStringBuilder.length() > 0 ) {
				queryStringBuilder.append( "&" );
			}
			queryStringBuilder.append( URLEncoder.encode( entry.getKey(), StandardCharsets.UTF_8 ) );
			queryStringBuilder.append( "=" );
			queryStringBuilder.append( URLEncoder.encode( entry.getValue(), StandardCharsets.UTF_8 ) );
		}

		return queryStringBuilder.toString();
	}
}
