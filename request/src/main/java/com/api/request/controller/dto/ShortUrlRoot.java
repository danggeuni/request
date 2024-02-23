package com.api.request.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortUrlRoot {
    private Result result;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Result {
        private String url;
        private String orgUrl;
    }
}
