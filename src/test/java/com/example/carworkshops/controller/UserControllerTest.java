package com.example.carworkshops.controller;

import com.example.carworkshops.controller.dto.UserCreateDto;
import com.example.carworkshops.model.City;
import com.example.carworkshops.model.User;
import com.example.carworkshops.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private List<User> users;

    @BeforeEach
    void setUp() {
        City city = City.builder()
            .id(4)
            .name("Berlin")
            .build();

        User user1 = User.builder()
            .id(1)
            .name("user1")
            .email("email1@google.com")
            .postalCode("232323")
            .city(city)
            .build();

        User user2 = User.builder()
            .id(2)
            .name("user2")
            .email("email2@google.com")
            .postalCode("232323")
            .city(city)
            .build();

        users = Arrays.asList(user1, user2);
    }

    @Test
    void shouldFetchAllUsers() throws Exception {
        given(userService.findAll()).willReturn(users);

        mockMvc.perform(get("/api/user/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.length()", is(users.size())));
    }

    @Test
    void shouldFetchOneUserById() throws Exception {
        long userId = 1L;
        User user = users.get(0);

        given(userService.getById(userId)).willReturn(users.get(0));

        mockMvc.perform(get("/api/user/{id}/", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.email", is(user.getEmail())))
            .andExpect(jsonPath("$.data.name", is(user.getName())));
    }


    @Test
    void shouldCreateNewUser() throws Exception {
        User user = users.get(0);

        UserCreateDto dto = UserCreateDto.builder()
            .name("user1")
            .email("email1@google.com")
            .postalCode("232323")
            .cityId(4)
            .build();

        given(userService.create(any(UserCreateDto.class))).willReturn(user);

        mockMvc.perform(post("/api/user/")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.email", is(dto.getEmail())))
            .andExpect(jsonPath("$.data.postal_code", is(dto.getPostalCode())))
            .andExpect(jsonPath("$.data.name", is(dto.getName())));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        long userId = 1L;
        User user = users.get(0);

        given(userService.getById(userId)).willReturn(user);

        doNothing().when(userService).delete(user.getId());

        mockMvc.perform(delete("/api/user/{id}/", user.getId()))
            .andExpect(jsonPath("$").doesNotExist());
    }

}