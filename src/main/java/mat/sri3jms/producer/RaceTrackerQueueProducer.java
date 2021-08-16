package mat.sri3jms.producer;

import lombok.RequiredArgsConstructor;
import mat.sri3jms.config.JmsConfig;
import mat.sri3jms.model.Race;
import mat.sri3jms.model.RaceMessage;
import mat.sri3jms.model.bolide.Bolide;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RaceTrackerQueueProducer {
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendInfo() {
        Bolide bolide = Race.getBolideInRace();
        RaceMessage message = RaceMessage.builder()
                .id(RaceMessage.nextId())
                .createdAt(LocalDateTime.now())
                .message(bolide.getState().toString() + bolide.getState().getStateInfo())
                .build();
        jmsTemplate.convertAndSend(JmsConfig.ROUTER_QUEUE, message);
        System.out.println("++++++++++++++++++++++++++++RACETRACKER+++++++++++++++++++++++++++" + "\n"
                + "RaceTrackerQueueProducer.sendInfo sending info to router"+ "\n"
                + "++++++++++++++++++++++++++++RACETRACKER+++++++++++++++++++++++++++" );
    }
}
