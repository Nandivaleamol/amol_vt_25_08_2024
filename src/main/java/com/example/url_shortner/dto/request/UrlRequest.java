package com.example.url_shortner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request DTO for URL entity
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequest {
    private String destinationUrl;
}
