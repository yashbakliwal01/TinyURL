package com.tinyurl.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "urls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "short_code", nullable = false, unique = true)
	private String shortCode;
	
	@Column(name = "long_url", nullable = false, columnDefinition = "TEXT")
	private String longUrl;
	
	private LocalDateTime createdAt;
	
	private Long clickCount;
	
//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//	private LocalDateTime expiryDate;
	
//	private boolean isActive;
	
}
