package BTS.Utils.DTO.Request;

import BTS.Entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Data
public class LoginUserRequest {
    private String username;
    private String password;

    public static User fromLoginRequestToUserMapper(LoginUserRequest loginUserRequest){
        return User.builder()
                .email(loginUserRequest.getUsername())
                .password(loginUserRequest.password)
                .build();
    }
}
