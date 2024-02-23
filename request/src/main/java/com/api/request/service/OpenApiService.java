package com.api.request.service;

import com.api.request.controller.dto.Root;
import com.api.request.controller.dto.Text;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class OpenApiService {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public String translate(Text text){
        String requestText = text.getText();

        String clientId = "nh4FuT1AxfKgUPBcPevI";
        String clientSecret = "IJnRHHK8Sl";
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

        try {
            HttpPost post = new HttpPost(apiURL);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setHeader("X-Naver-Client-Id", clientId);
            post.setHeader("X-Naver-Client-Secret", clientSecret);

            requestText = URLEncoder.encode(requestText, StandardCharsets.UTF_8);
            StringEntity entity = new StringEntity("source=ko&target=en&text=" + requestText);
            post.setEntity(entity);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(post);

            String json = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            Root result = objectMapper.readValue(json, Root.class);

            return result.getMessage().getResult().getTranslatedText();

            // 방법 2
            // String translatedText = objectMapper.readTree(json).get("message").get("result").get("translatedText").asText();
        } catch (IOException e) {
            throw new RuntimeException("API 통신에 실패했습니다.", e);
        }
    }
}
