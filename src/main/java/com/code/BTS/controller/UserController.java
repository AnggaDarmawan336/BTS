package com.code.BTS.controller;

import com.code.BTS.security.JwtUtils;
import com.code.BTS.service.UserService;
import com.code.BTS.utils.dto.request.LoginUserRequest;
import com.code.BTS.utils.dto.request.RegisterUserRequest;
import com.code.BTS.utils.dto.response.LoginUserResponse;
import com.code.BTS.utils.dto.response.UserResponse;
import com.code.BTS.utils.dto.webResponse.Res;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse response = UserResponse.fromUser(userService.create(request));
        return Res.renderJson(response, "Register User Created Successfully", HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public LoginUserResponse login(@RequestBody LoginUserRequest request) {
        return userService.login(request);
    }

}
