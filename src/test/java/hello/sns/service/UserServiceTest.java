package hello.sns.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hello.sns.exception.ErrorCode;
import hello.sns.exception.SnsApplicationException;
import hello.sns.fixture.UserEntityFixture;
import hello.sns.model.entity.UserEntity;
import hello.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Test
    void 로그인이_정상동작한다() {

        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName, password,1);
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password,fixture.getPassword())).thenReturn(true);
        Assertions.assertDoesNotThrow(()->userService.login(userName,password));

    }

    @Test
    void 로그인시_유저가_존재하지_않으면_에러를_내뱉는다() {

        String userName = "userName";
        String password = "password";
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,()->userService.login(userName,password));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND,e.getErrorCode());


    }

    @Test
    void 로그인시_패스워드가_다르면_에러를_내뱉는다() {

        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";
        UserEntity fixture = UserEntityFixture.get(userName, password,1);
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,()->userService.login(userName,password));
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD,e.getErrorCode());


    }


    @Test
    void 회원가입이_정상동작한다() {
        String userName = "userName";
        String password = "password";


        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(encoder.encode("password")).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(userName, password,1));
        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));

    }

    @Test
    void 회원가입시_아이디가_중복되면_다르면_에러를_내뱉는다() {

        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName, password,1);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.encode("password")).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName, password));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class,()->userService.join(userName,password));
        Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME,e.getErrorCode());

    }
}
