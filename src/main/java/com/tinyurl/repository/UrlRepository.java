package com.tinyurl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tinyurl.entity.UrlEntity;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
	
	Optional<UrlEntity> findByShortCode(String shortCode);
	
	@Query("SELECT u FROM UrlEntity u WHERE u.shortCode = ?1")
	Optional<UrlEntity> fetchUrlByCode(String shortCode);
}
