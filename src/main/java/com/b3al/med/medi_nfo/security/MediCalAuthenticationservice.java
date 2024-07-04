package com.b3al.med.medi_nfo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class MediCalAuthenticationservice {

    @Value("${medical.server.url}")
    private String mediCalServerUrl;

    @Value("${medical.server.auth}")
    private String authEndpoint;

    @Value("${medical.user.username}")
    private String username;

    @Value("${medical.user.password}")
    private String password;

    public String authenticate() {
        String url = mediCalServerUrl + authEndpoint;

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> request = new HashMap<>();
        request.put("username", username);
        request.put("password", password);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if(response.getStatusCode() == HttpStatusCode.valueOf(200)) {
            log.info("medi-cal server successfull login");
            return response.getBody().get("accessToken").toString();
        } else {
            return null;
        }
    }
}
