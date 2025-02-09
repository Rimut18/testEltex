package com.example.testeltex.service;

import com.example.testeltex.exception.UsersNotFoundException;
import com.example.testeltex.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private RestClient restClient;

    private RestClient.RequestHeadersUriSpec<?> requestMock;
    private RestClient.RequestHeadersSpec<?> requestHeadersMock;
    private RestClient.ResponseSpec responseMock;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "externalApiUrl", "http://localhost:8080/users");
        
        requestMock = mock(RestClient.RequestHeadersUriSpec.class);
        requestHeadersMock = mock(RestClient.RequestHeadersSpec.class);
        responseMock = mock(RestClient.ResponseSpec.class);
        
        when(restClient.get()).thenAnswer(invocation -> (requestMock));
        when(requestMock.uri(anyString())).thenAnswer(invocation -> (requestHeadersMock));
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
    }


    @Test
    void doGetUsers_ShouldReturnUsersList() {
        List<User> mockUsers = List.of(new User("Ivanov Ivan", "Ivanovivan@example.com", 3000),
                new User("Petrov Petr", "Petrovpetr@example.com", 4000));

        when(responseMock.onStatus(any(), any())).thenReturn(responseMock);
        when(responseMock.body(any(ParameterizedTypeReference.class))).thenReturn(mockUsers);

        List<User> result = userService.doGetUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ivanov Ivan", result.get(0).name());
        assertEquals("Ivanovivan@example.com", result.get(0).email());
        assertEquals(3000, result.get(0).salary());
    }

    @Test
    void doGetUsers_ShouldThrowException() {
        when(responseMock.onStatus(any(), any())).thenThrow(new UsersNotFoundException("External service for loading users is not available"));

        assertThrows(UsersNotFoundException.class, () -> userService.doGetUsers());
    }
}
