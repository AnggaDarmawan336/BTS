package com.code.BTS.service.impl;

import com.code.BTS.entity.User;
import com.code.BTS.repository.UserRepository;
import com.code.BTS.security.JwtUtils;
import com.code.BTS.service.UserService;
import com.code.BTS.utils.dto.request.LoginUserRequest;
import com.code.BTS.utils.dto.request.RegisterUserRequest;
import com.code.BTS.utils.dto.response.LoginUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public User create(RegisterUserRequest request) {
        User newUser = RegisterUserRequest.fromRegisterToUserMapper(request);
        String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        newUser.setPassword(hashedPassword);
        return userRepository.saveAndFlush(newUser);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        LoginUserResponse loginResponse = new LoginUserResponse();
        loginResponse.setAccessToken("");
        try {
            User user = userRepository.findByUsername(request.getUsername());
            if(user.getUsername() != null) {
                Boolean isPasswordMatch = new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword());
                if(new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
                    String accessToken = jwtUtils.generateAccessToken(user);
                    loginResponse.setAccessToken(accessToken);
                }
            }
            return loginResponse;
        } catch (Exception error) {
            return loginResponse;
        }
    }
}
