package mat.sri3jms.receiver;

import lombok.RequiredArgsConstructor;
import mat.sri3jms.config.JmsConfig;
import mat.sri3jms.model.Race;
import mat.sri3jms.model.RaceMessage;
import mat.sri3jms.model.bolide.Bolide;
import mat.sri3jms.producer.RaceTrackerUtils;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RouterSenderAndReceiver {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ROUTER_QUEUE, containerFactory =
            "queueConnectionFactory")
    public void raceMessage(@Payload RaceMessage convertedMessage,
                            @Headers MessageHeaders messageHeaders,
                            Message message) {
        System.out.println("++++++++++++++++++++++++++++ROUTER+++++++++++++++++++++++++++");
        if (RaceTrackerUtils.isMessageStartsWithNormalState(convertedMessage)) {
            System.out.println("ROUTER: Sending To Monitoring app");
            jmsTemplate.convertAndSend(JmsConfig.RACE_QUEUE,convertedMessage);
        }
        if (RaceTrackerUtils.isMessageStartsWithWarningState(convertedMessage)){
            System.out.println("ROUTER: Sending To Driver and Monitoring app");
            jmsTemplate.convertAndSend(JmsConfig.DRIVER_QUEUE,convertedMessage);
            jmsTemplate.convertAndSend(JmsConfig.RACE_QUEUE,convertedMessage);
        }
        if (RaceTrackerUtils.isMessageStartsWithCriticalState(convertedMessage)){
            System.out.println("ROUTER: Sending To Mechanic and Monitoring app");
            jmsTemplate.convertAndSend(JmsConfig.MECHANIC_QUEUE,convertedMessage);
            jmsTemplate.convertAndSend(JmsConfig.RACE_QUEUE,convertedMessage);
        }
        System.out.println("++++++++++++++++++++++++++++ROUTER+++++++++++++++++++++++++++");
    }

}
