package com.api.request.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class Root {
    private Message message;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Message {
        private Result result;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Result {
            private String translatedText;
        }
    }
}