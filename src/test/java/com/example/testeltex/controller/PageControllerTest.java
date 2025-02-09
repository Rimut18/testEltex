package com.example.testeltex.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PageController.class)
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String frontendApiUrl = "http://localhost:8080/users";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(new PageController(), "frontendApiUrl", frontendApiUrl);
    }

    @Test
    void userTable_ShouldReturnUserTableView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTable"))
                .andExpect(model().attribute("apiUrl", frontendApiUrl));
    }
}