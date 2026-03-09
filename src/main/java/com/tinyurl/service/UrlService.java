package com.tinyurl.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tinyurl.cache.LRUCacheManager;
import com.tinyurl.dto.ShortenUrlResponse;
import com.tinyurl.dto.UrlInfoResponse;
import com.tinyurl.dto.UrlRequest;
import com.tinyurl.entity.UrlEntity;
import com.tinyurl.repository.UrlRepository;
import com.tinyurl.util.Base62Encoder;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UrlService {
	
	@Autowired
	UrlRepository repository;
	
	@Autowired
	Base62Encoder encoder;
	
	@Autowired
	LRUCacheManager cache;
	
	@Value("${app.base-url}")
	public String baseUrl;
	
	public ShortenUrlResponse shortenUrl(UrlRequest request) {
		
		log.info("Received request to shorten URL: {}", request.getLongUrl());
		
		String shortCode = encoder.generateRandomCode();
		
		log.info("Generated short code: {}", shortCode);
		UrlEntity url = UrlEntity.builder()
				.longUrl(request.getLongUrl())
				.shortCode(shortCode)
				.clickCount(0L)
				.createdAt(LocalDateTime.now())
				.build();
		
		repository.save(url);
		log.info("Saved URL entity to database with id: {}", url.getId());
		
		cache.put(shortCode, url);
		
		return new ShortenUrlResponse(shortCode, baseUrl + shortCode);
	}
	
	public String redirect(String shortCode) {
		log.info("Redirect request received for shortCode: {}", shortCode);		
		
		UrlEntity url = cache.get(shortCode);
		if(url!=null) {
			log.info("Cache hit for shortCode: {}", shortCode);
		}
		
		if(url==null) {
			log.info("Cache miss for shortCode: {}. Fetching from database.");
			url = repository.findByShortCode(shortCode).orElseThrow(()-> new RuntimeException("Short URL not found"));
			cache.put(shortCode, url);
		}
		
		url.setClickCount(url.getClickCount()+1);
		repository.save(url);
		log.info("Redirecting to original URL: {}.", url.getLongUrl());
		log.info("Updated click count: {}", url.getClickCount());
		return url.getLongUrl();
	}

	
	//get info on the usage analytics of the short URL
	public UrlInfoResponse getUrlInfo(String shortCode) {
		log.info("Info request received for shortCode: {}", shortCode);
		UrlEntity url = cache.get(shortCode);
		
		if(url!=null) {
			log.info("Cache hit for shortCode: {}", shortCode);
		}
		
		if(url==null) {
			log.info("Cache miss for shortCode: {}. Fetching from database.", shortCode);
			url = repository.findByShortCode(shortCode).orElseThrow(()-> new RuntimeException("Short URL not found"));
			cache.put(shortCode, url);
		}
		
		return new UrlInfoResponse(baseUrl + shortCode, 
				url.getLongUrl(),
				url.getClickCount(), 
				url.getCreatedAt());
	}
	
}
