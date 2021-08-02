package com.example.carworkshops.service;

import com.example.carworkshops.controller.dto.UserCreateDto;
import com.example.carworkshops.model.User;

import java.util.List;

public interface UserService {
    User create(UserCreateDto dto);

    User getById(long id);

    void delete(long id);

    List<User> findAll();
}
