package com.example.carworkshops.repository;

import com.example.carworkshops.model.User;
import com.example.carworkshops.repository.base.BaseRepository;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {

    List<User> findByNameAndDeletedAtIsNull(String name);

    List<User> findByEmailAndDeletedAtIsNull(String email);
}
