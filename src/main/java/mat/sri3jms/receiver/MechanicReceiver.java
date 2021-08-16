package mat.sri3jms.receiver;

import lombok.RequiredArgsConstructor;
import mat.sri3jms.config.JmsConfig;
import mat.sri3jms.model.DriverMessage;
import mat.sri3jms.model.Race;
import mat.sri3jms.model.RaceMessage;
import mat.sri3jms.model.bolide.Bolide;
import mat.sri3jms.producer.RaceTrackerUtils;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MechanicReceiver {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MECHANIC_QUEUE, containerFactory = "queueConnectionFactory")
    public void raceMessage(@Payload RaceMessage convertedMessage, @Headers MessageHeaders messageHeaders,
                            Message message) {
        System.out.println(
                "++++++++++++++++++++++++++++MECHANIC+++++++++++++++++++++++++++" + "\n"
                        + "MechanicReceiver.raceMessage MONITORING APP : " + convertedMessage + "\n"
                        + "++++++++++++++++++++++++++++MECHANIC+++++++++++++++++++++++++++"
        );
    }

    @JmsListener(destination = JmsConfig.MECHANIC_RESPOND_QUEUE)
    public void receiveAndRespond(@Payload DriverMessage convertedMessage,
                                  @Headers MessageHeaders headers,
                                  Message message) throws JMSException {
        System.out.println(
                "++++++++++++++++++++++++++++MECHANIC+++++++++++++++++++++++++++" + "\n"
                        + "MechanicReceiver.receiveAndRespond DRIVER MESSAGE: " + convertedMessage + "\n"
                        + "++++++++++++++++++++++++++++MECHANIC+++++++++++++++++++++++++++"
        );
        Destination replyTo = message.getJMSReplyTo();
        Bolide bolide = Race.getBolideInRace();
        DriverMessage msg;
        if (RaceTrackerUtils.isStateCritical(bolide) || RaceTrackerUtils.isTirePressureUnderMechanicAcceptance(bolide)) {
            msg = DriverMessage.builder()
                    .id(DriverMessage.nextId())
                    .createdAt(LocalDateTime.now())
                    .message("YES")
                    .build();
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||PITSTOP|||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||PITSTOP|||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||PITSTOP|||||||||||||||||||||||||||||||||||||||||||||||||");
            bolide.setTirePressure(1.5);
            bolide.setEngineTemperature(120);
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||PITSTOP|||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||PITSTOP|||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||PITSTOP|||||||||||||||||||||||||||||||||||||||||||||||||");
        } else {
            msg = DriverMessage.builder()
                    .id(DriverMessage.nextId())
                    .createdAt(LocalDateTime.now())
                    .message("NO")
                    .build();
        }
        jmsTemplate.convertAndSend(replyTo, msg);
    }
}
