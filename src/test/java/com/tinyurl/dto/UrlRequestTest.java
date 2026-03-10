package com.tinyurl.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlRequestTest {
	
	@Test
	void testUrlRequest() {
		UrlRequest urlRequest = new UrlRequest();
		urlRequest.setLongUrl("https://www.google.com");
		Assertions.assertEquals("https://www.google.com", urlRequest.getLongUrl());
	}
}
