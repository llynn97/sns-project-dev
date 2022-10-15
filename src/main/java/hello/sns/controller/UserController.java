package hello.sns.controller;

import hello.sns.controller.request.UserJoinRequest;
import hello.sns.controller.request.UserLoginRequest;
import hello.sns.controller.response.AlarmResponse;
import hello.sns.controller.response.Response;
import hello.sns.controller.response.UserJoinResponse;
import hello.sns.controller.response.UserLoginResponse;
import hello.sns.model.User;
import hello.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){

        User user = userService.join(request.getName(),request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }


    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request){
       String token =  userService.login(request.getName(), request.getPassword());
       return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication){
        return Response.success( userService.alarmList(authentication.getName(),pageable).map(AlarmResponse::fromAlarm));
    }
}
