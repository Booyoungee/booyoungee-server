package com.server.booyoungee.domain.topSceret.api;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcmeChallengeController {

	@GetMapping("/.well-known/acme-challenge/{token}")
	public byte[] getAcmeChallengeToken(String token) throws Exception {
		return Files.readAllBytes(Paths.get("/var/www/html/.well-known/acme-challenge/" + token));
	}
}