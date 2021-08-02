package com.example.carworkshops.controller;

import com.example.carworkshops.controller.dto.UserCreateDto;
import com.example.carworkshops.controller.validator.UserCreateValidator;
import com.example.carworkshops.model.DataResultObject;
import com.example.carworkshops.model.User;
import com.example.carworkshops.service.UserService;
import com.example.carworkshops.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserCreateValidator userCreateValidator;

    @Autowired
    public UserController(UserService userService,
                          UserCreateValidator userCreateValidator) {
        this.userService = userService;
        this.userCreateValidator = userCreateValidator;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<List<User>>> list() {

        return HttpUtils.ok(userService.findAll());
    }

    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<User>> get(
        @PathVariable Long id
    ) {

        return HttpUtils.ok(userService.getById(id));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResultObject<User>> create(
        @Valid @RequestBody UserCreateDto userCreateDto
    ) {
        User user = userService.create(userCreateDto);

        return HttpUtils.withStatus(user, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}/")
    public ResponseEntity<DataResultObject<Void>> delete(
        @PathVariable Long id
    ) {
        userService.delete(id);

        return HttpUtils.noContent();
    }

    @InitBinder("userCreateDto")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(userCreateValidator);
    }
}
