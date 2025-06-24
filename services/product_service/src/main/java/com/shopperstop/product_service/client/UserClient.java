package com.shopperstop.product_service.client;

import com.shopperstop.product_service.entity.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;



@Component
@RequiredArgsConstructor
public class UserClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${internal.service.URL}")
    private String URL;

    @Value("${internal.service.token}")
    private String privateToken;

    public UserDTO getUserByUsername(String username, String password){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Service-Token", privateToken);
        headers.setBasicAuth(username, password);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<UserDTO> response = restTemplate.exchange(
                    URL + username,
                    HttpMethod.GET,
                    entity,
                    UserDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e){
            return null;
        }
    }
}
