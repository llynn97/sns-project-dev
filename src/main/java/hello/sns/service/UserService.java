package hello.sns.service;


import hello.sns.exception.ErrorCode;
import hello.sns.exception.SnsApplicationException;
import hello.sns.model.Alarm;
import hello.sns.model.User;
import hello.sns.model.entity.AlarmEntity;
import hello.sns.model.entity.UserEntity;
import hello.sns.repository.AlarmEntityRepository;
import hello.sns.repository.UserEntityRepository;
import hello.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public User loadUserByUserName(String userName){
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(()->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded",userName)));
    }

    @Transactional
    public User join(String userName, String password){
        userEntityRepository.findByUserName(userName).ifPresent(it->{
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated",userName));
        });
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,encoder.encode(password)));

        return User.fromEntity(userEntity);

    }

    public String login(String userName, String password){

        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->new SnsApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded",userName)));
        if(!encoder.matches(password,userEntity.getPassword())){
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        //토큰 생성
        String token = JwtTokenUtils.generateToken(userName,secretKey,expiredTimeMs);
        return token;
    }

    public Page<Alarm> alarmList(Integer userId, Pageable pageable){
        return alarmEntityRepository.findAllByUserId(userId,pageable).map(Alarm::fromEntity);
    }
}
