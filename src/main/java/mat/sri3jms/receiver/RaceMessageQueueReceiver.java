package mat.sri3jms.receiver;

import mat.sri3jms.config.JmsConfig;
import mat.sri3jms.model.RaceMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RaceMessageQueueReceiver {
    @JmsListener(destination = JmsConfig.RACE_QUEUE, containerFactory =
            "queueConnectionFactory")
    public void raceMessage(@Payload RaceMessage convertedMessage,
                                    @Headers MessageHeaders messageHeaders,
                                    Message message) {
        System.out.println(
                "++++++++++++++++++++++++++++MONITORING+++++++++++++++++++++++++++" + "\n"
                        + "MONITORING APP: RaceMessageQueueReceiver.raceMessage, message: " + convertedMessage + "\n"
                        + "++++++++++++++++++++++++++++MONITORING+++++++++++++++++++++++++++"
        );
    }
}
