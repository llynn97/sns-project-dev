package hello.sns.service;

import hello.sns.exception.ErrorCode;
import hello.sns.exception.SnsApplicationException;
import hello.sns.model.AlarmArgs;
import hello.sns.model.AlarmType;
import hello.sns.model.entity.AlarmEntity;
import hello.sns.model.entity.UserEntity;
import hello.sns.repository.AlarmEntityRepository;
import hello.sns.repository.EmitterRepository;
import hello.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmService {

    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";

    private final EmitterRepository emitterRepository;
    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;

    public void send(AlarmType type, AlarmArgs arg, Integer receiverUserId) {

        UserEntity user = userEntityRepository.findById(receiverUserId).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND));

        AlarmEntity alarmEntity = alarmEntityRepository.save(AlarmEntity.of(user, type, arg));

        emitterRepository.get(receiverUserId).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().id(alarmEntity.getId().toString()).name(ALARM_NAME).data("new alarm"));
            } catch (IOException e) {
                emitterRepository.delete(receiverUserId);
                throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        }, () -> log.info("No Emitter founded"));

    }

    public SseEmitter connectAlarm(Integer userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId,sseEmitter);
        sseEmitter.onCompletion(()->emitterRepository.delete(userId));
        sseEmitter.onTimeout(()->emitterRepository.delete(userId));
        try {
            sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("connect completed"));
        } catch (IOException e) {
            throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
        }

        return sseEmitter;

    }
}
