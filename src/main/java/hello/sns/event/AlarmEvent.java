package hello.sns.event;

import hello.sns.model.AlarmArgs;
import hello.sns.model.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AlarmEvent {
    private Integer receiverUserId;
    private AlarmType alarmType;
    private AlarmArgs args;
}
