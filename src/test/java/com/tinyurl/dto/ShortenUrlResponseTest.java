package com.tinyurl.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShortenUrlResponseTest {
	
	@Test
	void testShortenUrlResponse() {
		ShortenUrlResponse response = new ShortenUrlResponse("abc123", "http://localhost:8080/abc123");
		response.setShortUrl("http://localhost:8080/abc123");
		
		Assertions.assertEquals("abc123", response.getShortCode());
		assert(response.getShortUrl().equals("http://localhost:8080/abc123"));
	}

}
