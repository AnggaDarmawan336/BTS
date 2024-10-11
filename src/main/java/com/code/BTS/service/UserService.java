package com.code.BTS.service;

import com.code.BTS.entity.User;
import com.code.BTS.utils.dto.request.LoginUserRequest;
import com.code.BTS.utils.dto.request.RegisterUserRequest;
import com.code.BTS.utils.dto.response.LoginUserResponse;

public interface UserService {
    User create(RegisterUserRequest request);
    LoginUserResponse login(LoginUserRequest request);
}
