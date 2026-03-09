package com.tinyurl.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {
	
	private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final Random RANDOM = new Random();
	
	public String encode(Long value) {
		
		StringBuilder sb = new StringBuilder();
		if(value==0) return "a";
		while (value > 0) {
			int remainder = (int)(value % 62);
			
			sb.append(BASE62.charAt(remainder));
			value /= 62;
		}
		return sb.reverse().toString();
	}
	
	public String generateRandomCode() {
	    StringBuilder sb = new StringBuilder();
	    for(int i=0; i<6; i++) {
	        int index = RANDOM.nextInt(BASE62.length());
	        sb.append(BASE62.charAt(index));
	    }
		
		return sb.toString();
	}
	
}
