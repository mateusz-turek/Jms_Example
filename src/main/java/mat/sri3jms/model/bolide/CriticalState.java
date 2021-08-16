package mat.sri3jms.model.bolide;

public class CriticalState implements BolideState {

    private final String stateInfo;

    public CriticalState(String information) {
        this.stateInfo = information;
    }

    @Override
    public void updateStatus(Bolide bolide) {
        StringBuilder infoOutput = new StringBuilder();
        if (BolideUtils.isEngineTemperatureSafe(bolide) && BolideUtils.isTirePressureSafe(bolide)) {
            infoOutput.append("engine temperature: ");
            infoOutput.append(bolide.getEngineTemperature());
            infoOutput.append(" ");
            infoOutput.append("tire pressure: ");
            infoOutput.append(bolide.getTirePressure());
            bolide.setState(new NormalState(infoOutput.toString()));
        } else {
            if (!BolideUtils.isEngineTemperatureSafe(bolide) && !BolideUtils.isTirePressureSafe(bolide)) {
                infoOutput.append("high engine temperature: ");
                infoOutput.append(bolide.getEngineTemperature());
                infoOutput.append(" ");
                infoOutput.append("low tire pressure: ");
                infoOutput.append(bolide.getTirePressure());
                bolide.setState(new CriticalState(infoOutput.toString()));
            } else {
                if (!BolideUtils.isEngineTemperatureSafe(bolide)) {
                    infoOutput.append("high engine temperature: ");
                    infoOutput.append(bolide.getEngineTemperature());
                    infoOutput.append(" ");
                } else {
                    infoOutput.append("engine temperature: ");
                    infoOutput.append(bolide.getEngineTemperature());
                    infoOutput.append(" ");
                }
                if (!BolideUtils.isTirePressureSafe(bolide)) {
                    infoOutput.append("low tire pressure: ");
                    infoOutput.append(bolide.getTirePressure());
                } else {
                    infoOutput.append("tire pressure: ");
                    infoOutput.append(bolide.getTirePressure());
                }
                bolide.setState(new WarningState(infoOutput.toString()));
            }
        }
    }

    @Override
    public String getStateInfo() {
        return stateInfo;
    }

    @Override
    public String toString() {
        return "CriticalState: ";
    }
}
