package com.tinyurl.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinyurl.entity.UrlEntity;

@ExtendWith(MockitoExtension.class)
public class UrlRepositoryTest {
	
	@Mock
	private UrlRepository urlRepository;
	
	@Test
	void testFindByShortCode() {
		
		UrlEntity entity = UrlEntity.builder()
				.shortCode("abc123")
				.longUrl("https://google.com")
				.build();
		
		Mockito.when(urlRepository.findByShortCode("abc123")).thenReturn(Optional.of(entity));
		
		Optional<UrlEntity> entityOpt = urlRepository.findByShortCode("abc123");
		Assertions.assertTrue(entityOpt.isPresent());
		Assertions.assertEquals("abc123", entityOpt.get().getShortCode());
		Assertions.assertEquals("https://google.com", entityOpt.get().getLongUrl());
		
	}
	
	@Test
	void testFindByShortCodeNotFound() {
		UrlEntity entity = UrlEntity.builder()
				.shortCode("abc123")
				.longUrl("https://google.com")
				.build();
		
		Mockito.when(urlRepository.fetchUrlByCode("abc123")).thenReturn(Optional.of(entity));
		
		Optional<UrlEntity> entityOpt = urlRepository.fetchUrlByCode("abc123");
		Assertions.assertTrue(entityOpt.isPresent());
		Assertions.assertEquals("abc123", entityOpt.get().getShortCode());
		Assertions.assertEquals("https://google.com", entityOpt.get().getLongUrl());
	}
}