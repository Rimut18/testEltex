package com.example.testeltex.controller;

import com.example.testeltex.model.User;
import com.example.testeltex.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private List<User> users;

    @BeforeEach
    void setUp() {
        users = List.of(new User("Ivanov Ivan", "Ivanovivan@example.com", 3000));
    }
    @Test
    void getUsers_shouldReturnList() throws Exception {
        when(userService.doGetUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ivanov Ivan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("Ivanovivan@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(3000));
    }
}