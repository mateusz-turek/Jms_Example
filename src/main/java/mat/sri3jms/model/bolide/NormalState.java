package mat.sri3jms.model.bolide;

public class NormalState implements BolideState {

    private final String stateInfo;

    public NormalState(String information) {
        this.stateInfo = information;
    }

    @Override
    public void updateStatus(Bolide bolide) {
        StringBuilder infoOutput = new StringBuilder();
        if (!BolideUtils.isEngineTemperatureSafe(bolide) || !BolideUtils.isTirePressureSafe(bolide)) {
            if (!BolideUtils.isEngineTemperatureSafe(bolide)) {
                infoOutput.append("high engine temperature: ");
                infoOutput.append(bolide.getEngineTemperature());
                infoOutput.append(" ");
            }
            if (!BolideUtils.isTirePressureSafe(bolide)) {
                infoOutput.append("low tire pressure: ");
                infoOutput.append(bolide.getTirePressure());
                infoOutput.append(" ");
            } else {
                infoOutput.append("tire pressure: ");
                infoOutput.append(bolide.getTirePressure());
                infoOutput.append(" ");
            }
            bolide.setState(new WarningState(infoOutput.toString()));

        } else {
            infoOutput.append("engine temperature: ");
            infoOutput.append(bolide.getEngineTemperature());
            infoOutput.append(" ");
            infoOutput.append("tire pressure: ");
            infoOutput.append(bolide.getTirePressure());
            infoOutput.append(" ");
            bolide.setState(new NormalState(infoOutput.toString()));
        }
    }

    @Override
    public String getStateInfo() {
        return stateInfo;
    }

    @Override
    public String toString() {
        return "NormalState: ";
    }
}
