package com.tinyurl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tinyurl.cache.LRUCacheManager;

@Configuration
public class AppConfig {
	
	@Value("${cache.max.size}")
	private int cacheSize;
	
	@Bean
	public LRUCacheManager lruCacheManager() {
		return new LRUCacheManager(cacheSize);
	}

}
