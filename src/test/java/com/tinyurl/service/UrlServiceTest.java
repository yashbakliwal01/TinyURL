package com.tinyurl.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinyurl.cache.LRUCacheManager;
import com.tinyurl.dto.ShortenUrlResponse;
import com.tinyurl.dto.UrlInfoResponse;
import com.tinyurl.dto.UrlRequest;
import com.tinyurl.entity.UrlEntity;
import com.tinyurl.repository.UrlRepository;
import com.tinyurl.util.Base62Encoder;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {
	
	@Mock
	private UrlRepository repository;
	
	@Mock
	private Base62Encoder encoder;
	
	@Mock
	private LRUCacheManager cache;
	
	@InjectMocks
	private UrlService urlService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		urlService.baseUrl = "http://localhost:8080/";
	}
	
	@Test
	void testShortenUrl() {
		UrlRequest request = new UrlRequest();
		request.setLongUrl("https://google.com");
		Mockito.when(encoder.generateRandomCode()).thenReturn("abc123");
		
		UrlEntity urlEntity = UrlEntity.builder()
				.longUrl(request.getLongUrl())
				.shortCode("abc123")
				.clickCount(0L)
				.build();
		
		Mockito.when(repository.save(any())).thenReturn(urlEntity);
		
		ShortenUrlResponse response = urlService.shortenUrl(request);
		Assertions.assertNotNull(response);
		Assertions.assertEquals("abc123", response.getShortCode());
		Assertions.assertEquals("http://localhost:8080/abc123", response.getShortUrl());
	}
	
	@Test
	void testRedirecrCacheHit() {
		UrlEntity urlEntity = UrlEntity.builder()
				.longUrl("https://google.com")
				.shortCode("abc123")
				.clickCount(0L)
				.build();
		
		Mockito.when(cache.get("abc123")).thenReturn(urlEntity);
		
		String result = urlService.redirect("abc123");
		Assertions.assertEquals("https://google.com", result);
		verify(repository, never()).findByShortCode("abc123");
		verify(repository, times(1)).save(urlEntity);
	}
	
	@Test
	void testRedirectCacheMiss() {
		UrlEntity urlEntity = UrlEntity.builder()
				.longUrl("https://google.com")
				.shortCode("abc123")
				.clickCount(0L)
				.build();
		Mockito.when(cache.get("abc123")).thenReturn(null);
		Mockito.when(repository.findByShortCode("abc123")).thenReturn(Optional.of(urlEntity));
		
		String result = urlService.redirect("abc123");
		Assertions.assertEquals("https://google.com", result);
		verify(repository, times(1)).findByShortCode("abc123");
		verify(repository, times(1)).save(urlEntity);
	}
	
	@Test
	void testRedirectNotFound() {
		Mockito.when(cache.get("abc123")).thenReturn(null);
		Mockito.when(repository.findByShortCode("abc123")).thenReturn(Optional.empty());
		
		Assertions.assertThrows(RuntimeException.class, () -> urlService.redirect("abc123"));
		verify(repository, times(1)).findByShortCode("abc123");
		verify(repository, never()).save(any());
	}
	
	@Test
	void testGetUrlInfoCacheHit() {
		UrlEntity urlEntity = UrlEntity.builder()
				.longUrl("https://google.com")
				.shortCode("abc123")
				.clickCount(5L)
				.build();
		
		Mockito.when(cache.get("abc123")).thenReturn(urlEntity);
		
		UrlInfoResponse response = urlService.getUrlInfo("abc123");
		Assertions.assertEquals("https://google.com", response.getLongUrl());
		Assertions.assertEquals(5L, response.getClickCount());
		verify(repository, never()).findByShortCode("abc123");
	}
	
	@Test
	void testGetUrlInfoCacheMiss() {
		UrlEntity urlEntity = UrlEntity.builder()
				.longUrl("https://google.com")
				.shortCode("abc123")
				.clickCount(5L)
				.build();
		
		Mockito.when(cache.get("abc123")).thenReturn(null);
		Mockito.when(repository.findByShortCode("abc123")).thenReturn(Optional.of(urlEntity));
		
		UrlInfoResponse response = urlService.getUrlInfo("abc123");
		Assertions.assertEquals("https://google.com", response.getLongUrl());
		Assertions.assertEquals(5L, response.getClickCount());
		verify(repository, times(1)).findByShortCode("abc123");
	}
	
	@Test
	void testGetUrlInfoNotFound() {
		Mockito.when(cache.get("abc123")).thenReturn(null);
		Mockito.when(repository.findByShortCode("abc123")).thenReturn(Optional.empty());
		
		Assertions.assertThrows(RuntimeException.class, () -> urlService.getUrlInfo("abc123"));
		verify(repository, times(1)).findByShortCode("abc123");
	}

}
