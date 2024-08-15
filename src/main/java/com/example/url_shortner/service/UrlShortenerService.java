package com.example.url_shortner.service;

import com.example.url_shortner.dto.response.UrlResponse;

import java.util.Optional;
import java.util.List;

/**
 * Service interface providing the abstract methods for URL entities service logic
 */
public interface UrlShortenerService {

    /**
     * Generates a shortened URL for a given destination URL.
     *
     * @param destinationUrl the original URL to shorten.
     * @return the UrlResponse DTO object containing saved URL details with shorten url string
     */
    UrlResponse shortenUrl(String destinationUrl);

    /**
     * Retrieves the destination URL for a given shortened URL.
     *
     * @param shortUrl the shortened URL string.
     * @return an Optional containing the destination URL if found and not expired, or empty if not found or expired.
     */
    Optional<String> getDestinationUrl(String shortUrl);

    /**
     * Updates the destination URL for a given shortened URL.
     *
     * @param shortUrl the shortened URL string.
     * @param newDestinationUrl the new destination URL to set.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateShortUrl(String shortUrl, String newDestinationUrl);

    /**
     * Updates the expiry date for a given shortened URL by adding the specified number of days.
     *
     * @param shortUrl the shortened URL string.
     * @param days the number of days to add to the expiry date.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateExpiry(String shortUrl, int days);

    /**
     * Retrieves a list of URl details by its ID.
     *
     * @param id the ID of the URL object to be retrieved
     * @return the UrlResponse DTO object containing the details of retrieved URL object.
     */
    UrlResponse getUrlDetailsById(Long id);

    /**
     * Retrieves a list of Url details
     *
     * @return A list of url response dto containing retrieved url details.
     */
    List<UrlResponse> getAllUrlDetails();


}
