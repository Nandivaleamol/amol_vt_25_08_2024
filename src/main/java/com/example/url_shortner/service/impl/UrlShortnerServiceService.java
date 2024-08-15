package com.example.url_shortner.service.impl;

import com.example.url_shortner.dto.response.UrlResponse;
import com.example.url_shortner.entity.Url;
import com.example.url_shortner.repository.UrlRepository;
import com.example.url_shortner.service.UrlShortenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation class providing the service logic for URL shortening and retrieval.
 */
@Service
@Slf4j
public class UrlShortnerServiceService implements UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    @Value("${app.url-prefix}")
    private String prefixUrl;

    /**
     * {@inheritDoc}
     */
    @Override
    public UrlResponse shortenUrl(String destinationUrl) {
        // Generate short URL
        String shortUrl = generateShortUrl();
        log.info("UrlShortnerServiceService::shortenUrl: Generated short URL {}", shortUrl);
        // Create URL new object
        Url url = new Url();

        // Set
        url.setShortUrl(shortUrl);
        url.setDestinationUrl(destinationUrl);
        url.setExpiryDate(LocalDateTime.now().plusMonths(10));
        var savedUrl = urlRepository.save(url);
        log.info("UrlShortnerServiceService::shortenUrl: URL saved successfully, Id {}", savedUrl.getId());
        return mapToResponse(savedUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getDestinationUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .filter(url -> url.getExpiryDate().isAfter(LocalDateTime.now()))
                .map(Url::getDestinationUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateShortUrl(String shortUrl, String newDestinationUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .map(url -> {
                    url.setDestinationUrl(newDestinationUrl);
                    urlRepository.save(url);
                    log.info("UrlShortnerServiceService::updateShortUrl: Url updated successfully");
                    return true;
                })
                .orElse(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateExpiry(String shortUrl, int days) {
        return urlRepository.findByShortUrl(shortUrl)
                .map(url -> {
                    url.setExpiryDate(url.getExpiryDate().plusDays(days));
                    urlRepository.save(url);
                    log.info("UrlShortnerServiceService::updateExpiry: Url expiry date updated successfully");
                    return true;
                })
                .orElse(false);
    }

    /**
     * {@inheritDoc}
     * @param id the ID of the URL object to be retrieved
     * @return
     */
    @Override
    public UrlResponse getUrlDetailsById(Long id) {
        var url = this.urlRepository.findById(id);
        return url.map(this::mapToResponse).orElseThrow(()-> new RuntimeException("Url details not found with ID " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UrlResponse> getAllUrlDetails() {
        return this.urlRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    /**
     * Generates a random base64 string to be used as a shortened URL.
     *
     * @return a random base64 string.
     */
    public String generateShortUrl() {
        var random = new SecureRandom();
        byte[] bytes = new byte[10]; // 10 bytes = 80 bits = 14 characters in Base64
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Maps to response dto
     *
     * @param url the URl entity object to be mapped
     * @return the UrlResponse DTO containing URL object details
     */
    private UrlResponse mapToResponse(Url url){
        var response = new UrlResponse();
        response.setId(url.getId());
        response.setShortUrl(url.getShortUrl());
        response.setDestinationUrl(url.getDestinationUrl());
        response.setExpiryDate(url.getExpiryDate());

        return response;
    }
}
