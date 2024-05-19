package com.example.SpringExercise.Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TruProxyClient<T> {
    private final RestTemplate restTemplate;

    @Autowired
    SecretsManagerUtil secretsManagerUtil ;
    public TruProxyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     *
     * @param queryUrl
     * @param responseClass
     * @return
     * @throws JsonProcessingException
     */
    public ResponseEntity<T> getAPIResponseFromTruProxyClient(String queryUrl, Class responseClass) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", secretsManagerUtil.getSecret());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println("Hitting company service with url: " + queryUrl + " and headers: {}" + headers);
        return restTemplate.exchange(
                queryUrl,
                HttpMethod.GET,
                entity,
                responseClass
        );
    }
}
