package mat.sri3jms.model.bolide;

import java.math.BigDecimal;

public class BolideUtils {

    public static boolean isEngineTemperatureSafe(Bolide bolide) {
        return bolide.getEngineTemperature() < BolideConstants.DANGEROUS_ENGINE_TEMPERATURE_VALUE;
    }

    public static boolean isTirePressureSafe(Bolide bolide) {
        return bolide.getTirePressure() > BolideConstants.DANGEROUS_TIRE_PRESSURE_VALUE;
    }

    public static void simulateRace(Bolide bolide) {
        Integer randomNumber = BolideConstants.MIN_RANGE_VALUE +
                (int) (Math.random() * (BolideConstants.MAX_RANGE_VALUE - BolideConstants.MIN_RANGE_VALUE));
        int operationVariable = 1 + (int) (Math.random() * (4 - 1));
        if (1 == operationVariable) {
            bolide.setEngineTemperature(bolide.getEngineTemperature() - randomNumber);
            BigDecimal tirePressure = BigDecimal.valueOf(bolide.getTirePressure());
            BigDecimal subtractValue = new BigDecimal(BolideConstants.TIRE_DECREMENT);
            BigDecimal result = tirePressure.subtract(subtractValue);
            if (0.00 <= result.doubleValue()) {
                bolide.setTirePressure(result.doubleValue());
            }
        } else {
            bolide.setEngineTemperature(bolide.getEngineTemperature() + randomNumber);
        }
    }
}
