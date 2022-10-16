package hello.sns.model;

import hello.sns.model.entity.AlarmEntity;
import hello.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class Alarm {

    private Integer id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity entity) {
        log.info("====Call fromEntity");
        return new Alarm(
                entity.getId(),
                entity.getAlarmType(),
                entity.getArgs(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
