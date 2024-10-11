package BTS.Service.Impl;

import BTS.Entity.User;
import BTS.Repository.UserRepository;
import BTS.Security.JwtUtils;
import BTS.Utils.DTO.Request.LoginUserRequest;
import BTS.Utils.DTO.Request.RegisterUserRequest;
import BTS.Utils.DTO.Response.LoginUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public User create(RegisterUserRequest request) {
        User newUser = RegisterUserRequest.fromRegisterToUserMapper(request);
        String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        newUser.setPassword(hashedPassword);
        return userRepository.saveAndFlush(newUser);
    }

    public LoginUserResponse login(LoginUserRequest request) {
        LoginUserResponse loginResponse = new LoginUserResponse();
        loginResponse.setAccessToken("");
        try {
            User user = userRepository.findByUsername(request.getUsername());
            if (user.getEmail() != null) {
                Boolean isPasswordMatch = new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword());
                if (new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
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
