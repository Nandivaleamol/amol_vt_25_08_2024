package com.example.url_shortner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Response DTO for URL response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlResponse {
    private Long id;
    private String shortUrl;
    private String destinationUrl;
    private LocalDateTime expiryDate;
}
