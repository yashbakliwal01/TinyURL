package com.tinyurl.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlEntityTest {
	
	@Test
	public void testUrlEntityBuilder() {
		
		UrlEntity urlEntity = UrlEntity.builder()
				.shortCode("abc123")
				.longUrl("https://google.com")
				.createdAt(LocalDateTime.now())
				.build();
		
		Assertions.assertEquals("abc123", urlEntity.getShortCode());
		Assertions.assertEquals("https://google.com", urlEntity.getLongUrl());
		Assertions.assertNotNull(urlEntity.getCreatedAt());
		
	}

}
