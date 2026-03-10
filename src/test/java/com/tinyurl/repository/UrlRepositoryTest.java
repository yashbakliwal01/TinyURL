package com.tinyurl.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tinyurl.entity.UrlEntity;

@DataJpaTest
public class UrlRepositoryTest {
	
	@Autowired
	private UrlRepository urlRepository;
	
	@Test
	void testFindByShortCode() {
		
		UrlEntity entity = UrlEntity.builder()
				.shortCode("abc123")
				.longUrl("https://google.com")
				.build();
		urlRepository.save(entity);
			
		String shortCode = "abc123";
		Optional<UrlEntity> entityOpt = urlRepository.findByShortCode(shortCode);
		
		Assertions.assertTrue(entityOpt.isPresent());
		Assertions.assertEquals(shortCode, entityOpt.get().getShortCode());
		Assertions.assertEquals("https://google.com", entityOpt.get().getLongUrl());
		
	}
	
	@Test
	void testFindByShortCodeNotFound() {
		UrlEntity entity = UrlEntity.builder()
				.shortCode("abc123")
				.longUrl("https://google.com")
				.build();
		urlRepository.save(entity);
			
		Optional<UrlEntity> entityOpt = urlRepository.fetchUrlByCode("abc123");
		Assertions.assertTrue(entityOpt.isPresent());
		Assertions.assertEquals("abc123", entityOpt.get().getShortCode());
		Assertions.assertEquals("https://google.com", entity.getLongUrl());
	}
}
