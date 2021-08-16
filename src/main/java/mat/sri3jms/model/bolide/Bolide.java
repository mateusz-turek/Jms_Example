package mat.sri3jms.model.bolide;

public class Bolide {

    private BolideState state;

    private Integer engineTemperature;

    private Double tirePressure;

    public Bolide() {
        this.state = new NormalState(null);
        this.engineTemperature = BolideConstants.INITIAL_ENGINE_TEMPERATURE_VALUE;
        this.tirePressure = BolideConstants.INITIAL_TIRE_PRESSURE_VALUE;
    }

    public void updateState() {
        this.state.updateStatus(this);
    }

    public void setState(BolideState bolideState) {
        this.state = bolideState;
    }

    public BolideState getState() {
        return state;
    }

    public Integer getEngineTemperature() {
        return engineTemperature;
    }

    public void setEngineTemperature(Integer engineTemperature) {
        this.engineTemperature = engineTemperature;
    }

    public Double getTirePressure() {
        return tirePressure;
    }

    public void setTirePressure(Double tirePressure) {
        this.tirePressure = tirePressure;
    }

}
