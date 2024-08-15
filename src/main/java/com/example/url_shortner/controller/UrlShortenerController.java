package com.example.url_shortner.controller;

import com.example.url_shortner.dto.response.UrlResponse;
import com.example.url_shortner.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

/**
 * REST controller providing endpoints for URL shortening, retrieval, and management.
 */
@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;


    /**
     * Endpoint to shorten a given destination URL.
     *
     * @param destinationUrl the original URL to shorten.
     * @return the UrlResponse DTO object containing URl details with shortened URL string.
     */
    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestParam String destinationUrl) {
        var response = urlShortenerService.shortenUrl(destinationUrl);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to redirect to the original destination URL when a shortened URL is accessed.
     *
     * @param response the HTTP servlet response to perform the redirect.
     * @param shortUrl the shortened URL string.
     * @throws IOException if an I/O error occurs during redirection.
     */
    @GetMapping("/{shortUrl}")
    public void redirectToFullUrl(HttpServletResponse response, @PathVariable String shortUrl) throws IOException {
        urlShortenerService.getDestinationUrl(shortUrl)
                .ifPresentOrElse(
                        fullUrl -> {
                            try {
                                response.sendRedirect(fullUrl);
                            } catch (IOException e) {
                                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not redirect to the full URL", e);
                            }
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found");
                        }
                );
    }

    /**
     * Endpoint to update the destination URL for a given shortened URL.
     *
     * @param shortUrl the shortened URL string.
     * @param newDestinationUrl the new destination URL to set.
     * @return a boolean indicating if the update was successful.
     */
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateShortUrl(@RequestParam String shortUrl, @RequestParam String newDestinationUrl) {
        boolean updated = urlShortenerService.updateShortUrl(shortUrl, newDestinationUrl);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to update the expiry date of a shortened URL by adding the specified number of days.
     *
     * @param shortUrl the shortened URL string.
     * @param days the number of days to add to the expiry date.
     * @return a boolean indicating if the update was successful.
     */
    @PostMapping("/update-expiry")
    public ResponseEntity<Boolean> updateExpiry(@RequestParam String shortUrl, @RequestParam int days) {
        boolean updated = urlShortenerService.updateExpiry(shortUrl, days);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to retrieve the details of url entity
     *
     * @param id the ID of the URl entity object to be found.
     * @return the retrieved url response containing url entity details.
     */
    @GetMapping(value = "/get")
    public ResponseEntity<UrlResponse> getUrlDetailById(@RequestParam Long id){
        var response = this.urlShortenerService.getUrlDetailsById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to retrieve the all existing url details.
     * @return a ResponseEntity with list of retrieved url responses
     */
    @GetMapping(value = "/get/all")
    public ResponseEntity<List<UrlResponse>> getAllUrlDetails(){
        var urlResponseList = this.urlShortenerService.getAllUrlDetails();
        return ResponseEntity.ok(urlResponseList);
    }
}
