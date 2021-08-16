package mat.sri3jms.model.bolide;

public interface BolideState {

    void updateStatus(Bolide bolide);

    String getStateInfo();
}
