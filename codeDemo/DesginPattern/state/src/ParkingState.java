public class ParkingState extends StateBase {


    @Override
    public void switchToPARKING() {
        System.out.println("P档不变");



    }

    @Override
    public void switchToECO() {

        System.out.println("P档切换到E档");

    }

    @Override
    public String toString() {
        return "PARKING_STATE";
    }
}
