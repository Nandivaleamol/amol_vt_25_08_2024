package com.example.url_shortner.repository;

import com.example.url_shortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing `Url` entities.
 */
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    /**
     * Finds a URL entry by its shortened URL string.
     *
     * @param shortUrl the shortened URL string.
     * @return an Optional containing the URL entry if found, or empty if not found.
     */
    Optional<Url> findByShortUrl(String shortUrl);
}
