package com.tinyurl.dto;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlInfoResponseTest {
	
	@Test
	void testUrlInfoResponse() {
		UrlInfoResponse response = new UrlInfoResponse(
				"http://localhost:8080/abc123",
				"https://google.com",
				10L,
				LocalDateTime.now());
		
		Assertions.assertEquals("http://localhost:8080/abc123", response.getShortUrl());
		Assertions.assertEquals("https://google.com", response.getLongUrl());
		Assertions.assertEquals(10L, response.getClickCount());
		Assertions.assertNotNull(response.getCreatedAt());
	}
}
