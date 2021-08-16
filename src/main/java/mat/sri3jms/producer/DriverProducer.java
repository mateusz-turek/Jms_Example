package mat.sri3jms.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mat.sri3jms.config.JmsConfig;
import mat.sri3jms.model.DriverMessage;
import mat.sri3jms.model.Race;
import mat.sri3jms.model.bolide.Bolide;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DriverProducer {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    @Scheduled(fixedRate = 10000)
    public void askForHelp() throws JMSException, JsonProcessingException {
        Bolide bolide = Race.getBolideInRace();
        if(RaceTrackerUtils.isStateCriticalOrWarning(bolide)){
            DriverMessage message = DriverMessage.builder()
                    .id(DriverMessage.nextId())
                    .createdAt(LocalDateTime.now())
                    .message("Can I stop at Pit Stop")
                    .build();
            TextMessage responseMessage = (TextMessage) jmsTemplate.sendAndReceive(
                    JmsConfig.MECHANIC_RESPOND_QUEUE, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            TextMessage plainMessage = session.createTextMessage();
                            try {
                                plainMessage.setText(objectMapper.writeValueAsString(message));
                                plainMessage.setStringProperty("_type",
                                        DriverMessage.class.getName());
                                return plainMessage;
                            } catch (JsonProcessingException e) {
                                throw new JMSException("conversion to json failed: " +
                                        e.getMessage());
                            }
                        }
                    });
            String responseText = responseMessage.getText();
            DriverMessage responseConverted = objectMapper.readValue(responseText,
                    DriverMessage.class);
            System.out.println("DriverProducer.askForHelp got response: "
                    +responseText+"\n\tconvertedMessage: "+responseConverted);
        }
    }
}
