package com.server.booyoungee.domain.topSceret.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WebhookController {

	@Value("${movies.api.key}")
	private String verifyTokens;

	@GetMapping("/webhook")
	public String verifyWebhook(@RequestParam("hub.mode") String mode,
		@RequestParam("hub.verify_token") String verifyToken,
		@RequestParam("hub.challenge") String challenge) {
		if ("subscribe".equals(mode) && verifyTokens.equals(verifyToken)) {
			return challenge;
		} else {
			return "Verification failed";
		}
	}

}
