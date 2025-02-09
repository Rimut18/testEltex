package com.example.testeltex.service;

import com.example.testeltex.model.User;
import com.example.testeltex.exception.UsersNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestClient restClient;

    @Value("${external.api.url}")
    private String externalApiUrl;

    @Override
    public List<User> doGetUsers() {
        return restClient.get()
                .uri(externalApiUrl)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new UsersNotFoundException("External service for loading users is not available");
                })
                .body(new ParameterizedTypeReference<List<User>>() {});
    }
}
