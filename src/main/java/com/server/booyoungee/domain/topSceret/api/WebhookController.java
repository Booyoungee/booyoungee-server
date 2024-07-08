package com.server.booyoungee.domain.topSceret.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebhookController {

	@Value("${movies.api.key}")
	private String verifyTokens;

	@GetMapping("")
	public String verifyWebhook(@RequestParam("hub.mode") String mode,
		@RequestParam("hub.verify_token") String verifyToken,
		@RequestParam("hub.challenge") String challenge) {
		// Verify the webhook
		String VERIFY_TOKEN = verifyTokens;
		System.out.print("hello world");
		if (mode.equals("subscribe") && verifyToken.equals(VERIFY_TOKEN)) {
			System.out.print("hello world");
			return challenge;
		} else {
			return "Verification failed";
		}
	}
}
