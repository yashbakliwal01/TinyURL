package com.tinyurl.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.tinyurl.entity.UrlEntity;

public class LRUCacheManagerTest {
	
	@Test
	void testPutAndGet() {
		LRUCacheManager cacheManager = new LRUCacheManager(2);
		UrlEntity urlEntity = UrlEntity.builder()
				.longUrl("https://google.com")
				.shortCode("abc123")
				.clickCount(0L)
				.build();
		
		cacheManager.put("abc123", urlEntity);
		
		UrlEntity cachedEntity = cacheManager.get("abc123");
		Assertions.assertNotNull(cachedEntity);
		Assertions.assertEquals("https://google.com", cachedEntity.getLongUrl());
		
	}
	
	@Test
	void testCacheMiss() {
		LRUCacheManager cacheManager = new LRUCacheManager(2);
		UrlEntity cachedEntity = cacheManager.get("nonexistent");
		Assertions.assertNull(cachedEntity);
	}
	
	@Test
	void testEviction() {
		LRUCacheManager cacheManager = new LRUCacheManager(2);
		
		UrlEntity url1 = UrlEntity.builder()
				.longUrl("https://tinyurl1.com")
				.shortCode("t1")
				.clickCount(0L)
				.build();
		
		UrlEntity url2 = UrlEntity.builder()
				.longUrl("https://tinyurl2.com")
				.shortCode("t2")
				.clickCount(0L)
				.build();
		
		UrlEntity url3 = UrlEntity.builder()
				.longUrl("https://tinyurl3.com")
				.shortCode("t3")
				.clickCount(0L)
				.build();
		
		cacheManager.put("t1", url1);
		cacheManager.put("t2", url2);
		
		//Adding third then it should evict first one (t1)
		cacheManager.put("t3", url3);
		
		Assertions.assertNull(cacheManager.get("t1"));//gets evicted
		
		Assertions.assertNotNull(cacheManager.get("t2"));
		Assertions.assertNotNull(cacheManager.get("t3"));
	}
}
