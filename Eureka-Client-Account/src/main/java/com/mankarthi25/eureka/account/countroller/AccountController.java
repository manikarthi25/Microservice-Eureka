package com.mankarthi25.eureka.account.countroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

	@GetMapping("/status")
	public String getAccountAppStatus() {
		return " Account App is working";
	}

}
