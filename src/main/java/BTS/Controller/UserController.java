package BTS.Controller;


import BTS.Security.JwtUtils;
import BTS.Service.UserService;
import BTS.Utils.DTO.Request.LoginUserRequest;
import BTS.Utils.DTO.Request.RegisterUserRequest;
import BTS.Utils.DTO.Response.LoginUserResponse;
import BTS.Utils.DTO.Response.UserResponse;
import BTS.Utils.DTO.WebResponse.Res;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse response = UserResponse.fromUser(userService.create(request));
        return Res.renderJson(response, "Register User Created Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginUserResponse login(@RequestBody LoginUserRequest request) {
        return userService.login(request);
    }
}
