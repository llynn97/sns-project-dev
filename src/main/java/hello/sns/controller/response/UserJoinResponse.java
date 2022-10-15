package hello.sns.controller.response;

import hello.sns.model.User;
import hello.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserJoinResponse {

    private Integer id;
    private String userName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user){
        return new UserJoinResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRole()
        );
    }
}
