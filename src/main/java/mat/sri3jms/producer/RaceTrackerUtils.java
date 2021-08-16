package mat.sri3jms.producer;

import mat.sri3jms.model.RaceMessage;
import mat.sri3jms.model.bolide.*;

public class RaceTrackerUtils {

    private static final String NORMAL_STATE = "NormalState";
    private static final String WARNING_STATE = "WarningState";
    private static final String CRITICAL_STATE = "CriticalState";

    public static final Integer RACE_UPDATE_TIME = 1000;
    public static final Integer SEND_MESSAGE_TO_INFO_TIME = 15000;
    public static final Integer DRIVER_ASK_FOR_HELP_MESSAGE_TIME = 5000;

    public static Boolean isTirePressureUnderMechanicAcceptance(Bolide bolide){
        return bolide.getTirePressure() < BolideConstants.MECHANIC_ACCEPTANCE_PRESSURE_VALUE;
    }

    public static Boolean isStateCriticalOrWarning(Bolide bolide){
        BolideState state = bolide.getState();
        return state instanceof CriticalState || state instanceof WarningState;
    }

    public static Boolean isStateCritical(Bolide bolide){
        return bolide.getState() instanceof CriticalState;
    }

    public static Boolean isMessageStartsWithNormalState(RaceMessage raceMessage){
        return raceMessage.getMessage().startsWith(NORMAL_STATE);
    }

    public static Boolean isMessageStartsWithWarningState(RaceMessage raceMessage){
        return raceMessage.getMessage().startsWith(WARNING_STATE);
    }

    public static Boolean isMessageStartsWithCriticalState(RaceMessage raceMessage){
        return raceMessage.getMessage().startsWith(CRITICAL_STATE);
    }
}
