package com.example.carworkshops.service.impl;

import com.example.carworkshops.controller.dto.UserCreateDto;
import com.example.carworkshops.model.City;
import com.example.carworkshops.model.User;
import com.example.carworkshops.repository.UserRepository;
import com.example.carworkshops.service.CityService;
import com.example.carworkshops.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CityService cityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CityService cityService) {
        this.userRepository = userRepository;
        this.cityService = cityService;
    }

    @Override
    public User create(UserCreateDto dto) {
        City city = cityService.getById(dto.getCityId());

        User user = User.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .city(city)
            .postalCode(dto.getPostalCode())
            .build();

        return userRepository.save(user);
    }

    @Override
    public User getById(long id) {
        return userRepository
            .findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new NoSuchElementException("User not found, id = " + id));
    }

    @Override
    public void delete(long id) {
        User user = getById(id);

        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAllByDeletedAtIsNull();
    }
}
