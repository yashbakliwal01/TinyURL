package com.tinyurl.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tinyurl.entity.UrlEntity;


public class LRUCacheManager {
	
	private Map<String, UrlEntity> cache;
	
	public LRUCacheManager(int maxSize) {
		cache = new LinkedHashMap<String, UrlEntity>(maxSize, 0.75f, true){
			protected boolean removeEldestEntry(Map.Entry<String, UrlEntity> oldestEntry) {
				return size() > maxSize;
			}
		};
	}
	//0.75f is the load factor ==> Java's default load factor.
	//if map becomes 75% full --> then it resizes
	//LinkedHashMap(int capacity, float loadFactor, boolean accessOrder)
	
	public UrlEntity get(String key) {
		return cache.get(key);
	}
	
	public void put(String key, UrlEntity value) {
		cache.put(key, value);
	}
}
