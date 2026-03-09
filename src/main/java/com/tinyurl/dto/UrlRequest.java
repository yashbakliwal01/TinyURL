package com.tinyurl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UrlRequest {
	
	@NotBlank(message = "Long URL cannot be empty")
	private String longUrl;
	
//	private String customAlias;
//	
//	private LocalDateTime expiryDate;
}
