package me.nightletter.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/api/order" )
@RequiredArgsConstructor
public class OrderController {

	@GetMapping
	public String index() {
		return "index.html";
	}
}
