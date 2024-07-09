package com.server.booyoungee.domain.topSceret.api;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebhookController {

	private final ObjectMapper objectMapper;

	@Value("${instagram.access_token}")
	private String verifyTokens;
	@Value("${instagram.client_id}")
	private String CLIENT_ID;
	@Value("${instagram.client_secret}")
	private String CLIENT_SECRET;
	@Value("${instagram.redirect_url}")
	private String REDIRECT_URI;
	@Value("${instagram.access_token}")
	private String ACCESS_TOKEN;

	@GetMapping("")
	public String verifyWebhook(@RequestParam("hub.mode") String mode,
		@RequestParam("hub.verify_token") String verifyToken,
		@RequestParam("hub.challenge") String challenge) {
		if ("subscribe".equals(mode) && verifyTokens.equals(verifyToken)) {
			return challenge;
		} else {
			return "Verification failed";
		}
	}

	@PostMapping("")
	public void handleWebhook(@RequestBody String payload) {
		try {
			JsonNode json = objectMapper.readTree(payload);
			if (json.has("entry")) {
				for (JsonNode entry : json.get("entry")) {
					if (entry.has("messaging")) {
						for (JsonNode messaging : entry.get("messaging")) {
							if (messaging.has("message") && messaging.get("message").has("text")) {
								String messageText = messaging.get("message").get("text").asText();
								// URL 추출
								Pattern urlPattern = Pattern.compile(
									"(https?:\\/\\/www\\.(?:instagram\\.com|instagr\\.am)\\/p\\/\\S+)");
								Matcher matcher = urlPattern.matcher(messageText);
								if (matcher.find()) {
									String instagramUrl = matcher.group(1);
									System.out.println("Extracted URL: " + instagramUrl);
									// 이 URL을 Instagram Basic Display API를 통해 처리합니다.
									String postDetails = getInstagramPostDetails(instagramUrl);
									System.out.println("Instagram Post Details: " + postDetails);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getInstagramPostDetails(String instagramUrl) {
		// Instagram 게시물 ID 추출
		String postId = extractPostId(instagramUrl);
		String apiUrl = "https://graph.instagram.com/" + postId
			+ "?fields=id,caption,media_type,media_url,permalink,thumbnail_url,timestamp,username&access_token="
			+ ACCESS_TOKEN;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
		return response.getBody();
	}

	private String extractPostId(String instagramUrl) {
		// 예시 URL: https://www.instagram.com/p/POST_ID/
		Pattern pattern = Pattern.compile("https://www\\.instagram\\.com/p/(\\S+)");
		Matcher matcher = pattern.matcher(instagramUrl);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			throw new IllegalArgumentException("Invalid Instagram URL");
		}
	}

	@GetMapping("/auth")
	public String handleAuth(@RequestParam("code") String code) {
		// 액세스 토큰 교환 로직
		String apiUrl = "https://api.instagram.com/oauth/access_token";
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("client_id", CLIENT_ID);
		requestMap.put("client_secret", CLIENT_SECRET);
		requestMap.put("grant_type", "authorization_code");
		requestMap.put("redirect_uri", REDIRECT_URI);
		requestMap.put("code", code);

		ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestMap, String.class);
		try {
			JsonNode json = objectMapper.readTree(response.getBody());
			ACCESS_TOKEN = json.get("access_token").asText();
			return "Authenticated successfully, access token: " + ACCESS_TOKEN;
		} catch (Exception e) {
			e.printStackTrace();
			return "Authentication failed";
		}
	}

	@PostMapping("/deauthorize")
	public ResponseEntity<String> handleDeauthorize(@RequestBody String payload) {
		try {
			JsonNode json = objectMapper.readTree(payload);
			// deauthorize 로직을 추가합니다.
			String userId = json.get("user_id").asText();
			System.out.println("User ID: " + userId);
			// 사용자 데이터 삭제 또는 처리 로직
			return ResponseEntity.ok("Deauthorized successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Deauthorization failed");
		}
	}

	@PostMapping("/data_deletion")
	public ResponseEntity<Map<String, String>> handleDataDeletion(@RequestBody String payload) {
		try {
			JsonNode json = objectMapper.readTree(payload);
			String userId = json.get("user_id").asText();
			System.out.println("Data Deletion Request for User ID: " + userId);
			// 데이터 삭제 로직을 추가합니다.

			// 응답 데이터 구성
			Map<String, String> response = new HashMap<>();
			response.put("url", "https://post2trip.site/data_deletion_status");
			response.put("confirmation_code", "deletion-request-" + userId);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> response = new HashMap<>();
			response.put("error", "Data deletion failed");
			return ResponseEntity.status(500).body(response);
		}
	}

}
