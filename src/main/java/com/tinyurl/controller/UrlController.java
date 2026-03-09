package com.tinyurl.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tinyurl.dto.ShortenUrlResponse;
import com.tinyurl.dto.UrlInfoResponse;
import com.tinyurl.dto.UrlRequest;
import com.tinyurl.service.UrlService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
	
	@Autowired
	private UrlService urlService;
	
	
	//helps to shorten the long urls
	@PostMapping("/shorten")
	public ResponseEntity<ShortenUrlResponse> shorten(@Valid @RequestBody UrlRequest urlRequest) {
		ShortenUrlResponse response = urlService.shortenUrl(urlRequest);
		return ResponseEntity.ok(response);
	}
	
	
	//redirects the short url to the original long url
	@GetMapping("/{shortCode}")
	public ResponseEntity<Void> redirect(@PathVariable String shortCode){
		String url = urlService.redirect(shortCode);
		return ResponseEntity.status(302).location(URI.create(url)).build();
	}
	
	
	//usage analytics for the short url
	@GetMapping("/info")
	public ResponseEntity<UrlInfoResponse> getInfo(@RequestParam String shortCode){
		UrlInfoResponse urlResponse = urlService.getUrlInfo(shortCode);
		return ResponseEntity.ok(urlResponse);
	}

}
