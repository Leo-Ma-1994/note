public class Context {
    public StateBase PARKING_STATE = new ParkingState();
    public StateBase ECO_STATE = new EcoState();
    private StateBase stateBase;

    public Context(){
        this.stateBase = PARKING_STATE;
    }

    public StateBase getState() {
        System.out.println();
        return stateBase;
    }

    public void setState(StateBase stateBase) {
        this.stateBase = stateBase;
    }

    public void switchToECO() {

        this.stateBase.switchToECO();
        this.setState(ECO_STATE);
    }

    public void switchToPARKING(){

        this.stateBase.switchToPARKING();
        this.setState(PARKING_STATE);

    }


}
