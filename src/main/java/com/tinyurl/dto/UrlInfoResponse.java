package com.tinyurl.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlInfoResponse {
	
	private String shortUrl;
	private String longUrl;
    private long clickCount;
    private LocalDateTime createdAt;
	
}
