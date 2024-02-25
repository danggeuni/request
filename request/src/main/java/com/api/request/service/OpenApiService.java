package com.api.request.service;

import com.api.request.controller.dto.ShortUrlRoot;
import com.api.request.controller.dto.TranslateRoot;
import com.api.request.controller.dto.Text;
import com.api.request.controller.dto.UrlAddress;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

    private static final String CLIENT_ID = "nh4FuT1AxfKgUPBcPevI";
    private static final String CLIENT_SECRET = "IJnRHHK8Sl";
    private static final String API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    public String translate(Text text) {
        String requestText = text.getText();
        HttpPost post = new HttpPost(API_URL);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setHeader("X-Naver-Client-Id", CLIENT_ID);
        post.setHeader("X-Naver-Client-Secret", CLIENT_SECRET);
        String encodedText = URLEncoder.encode(requestText, StandardCharsets.UTF_8);

        try {
            StringEntity entity = new StringEntity("source=ko&target=en&text=" + encodedText);
            post.setEntity(entity);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(post);

            String json = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            TranslateRoot result = objectMapper.readValue(json, TranslateRoot.class);

            return result.getMessage().getResult().getTranslatedText();

            // 방법 2
            // return objectMapper.readTree(json).get("message").get("result").get("translatedText").asText();
        } catch (IOException e) {
            throw new RuntimeException("API 통신에 실패했습니다.", e);
        }
    }

    public ShortUrlRoot shortUrl(UrlAddress url) {
        String originalURL = url.getUrl();
        String clientId = "VTHdS8MlzGztZM8OebIH";
        String clientSecret = "i_BC_3X6qV";
        String apiURL = "https://openapi.naver.com/v1/util/shorturl?url=" + originalURL;

        try {
            HttpGet get = new HttpGet(apiURL);
            get.setHeader("Content-Type", "x-www-form-urlencoded; UTF-8");
            get.setHeader("X-Naver-Client-Id", clientId);
            get.setHeader("X-Naver-Client-Secret", clientSecret);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(get);

            String json = EntityUtils.toString(response.getEntity());

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, ShortUrlRoot.class);

        } catch (IOException e) {
            throw new RuntimeException("API 연결에 실패함요", e);
        }
    }

}
