package hello.sns.consumer;


import hello.sns.event.AlarmEvent;
import hello.sns.model.Alarm;
import hello.sns.service.AlarmService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Data
@RequiredArgsConstructor
@Slf4j
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack) {
        log.info("Consume the event {}", event);
        alarmService.send(event.getAlarmType(), event.getArgs(), event.getReceiverUserId());
        ack.acknowledge();
    }
}
