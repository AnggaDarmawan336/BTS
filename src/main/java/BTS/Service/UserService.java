package BTS.Service;

import BTS.Entity.User;
import BTS.Utils.DTO.Request.LoginUserRequest;
import BTS.Utils.DTO.Request.RegisterUserRequest;
import BTS.Utils.DTO.Response.LoginUserResponse;

public interface UserService {
    User create(RegisterUserRequest request);
    LoginUserResponse login(LoginUserRequest request);
}
