package mat.sri3jms.model;


import mat.sri3jms.model.bolide.Bolide;
import mat.sri3jms.model.bolide.BolideUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Race {

    private static final Bolide bolide = new Bolide();

    @Scheduled(fixedRate = 1750)
    public void race() {
        BolideUtils.simulateRace(bolide);
        bolide.updateState();
    }

    public static Bolide getBolideInRace(){
        return bolide;
    }
}
