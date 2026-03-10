package com.tinyurl.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tinyurl.dto.ShortenUrlResponse;
import com.tinyurl.dto.UrlInfoResponse;
import com.tinyurl.service.UrlService;


@WebMvcTest(UrlController.class)
public class UrlControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UrlService urlService;
	
	@Test
	void testShortenUrl() throws Exception {
		
		Mockito.when(urlService.shortenUrl(Mockito.any())).thenReturn(new ShortenUrlResponse("abc123","http://localhost:8080/abc123"));
		mockMvc.perform(post("/api/urls/shorten")
					.contentType("application/json")
					.content("{\"longUrl\":\"https://google.com\"}")
					)
		.andExpect(status().isOk());
	}
	
	@Test
	void testRedirect() throws Exception {
		Mockito.when(urlService.redirect("abc123")).thenReturn("https://google.com");
		mockMvc.perform(get("/api/urls/abc123"))
		.andExpect(status().isFound());
	}
	
	@Test
	void testGetInfo() throws Exception {
		Mockito.when(urlService.getUrlInfo("abc123"))
				.thenReturn(new UrlInfoResponse(
						"http://localhost:8080/abc123",
						"https://google.com",
						5L,
						LocalDateTime.now()));
		
		mockMvc.perform(get("/api/urls/info")
					.param("shortCode", "abc123"))
		.andExpect(status().isOk());
	}

}
