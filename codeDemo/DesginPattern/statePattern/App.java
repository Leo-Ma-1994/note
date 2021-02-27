

public class App {

    private static GearState mGearState = new GearState();

    public static void main(String[] args) {
        mGearState.setAction(GearEnum.PARKING, ParkingAction);
        mGearState.setAction(GearEnum.ECHO, EchoAction);
        mGearState.setAction(GearEnum.SPORT, SportAction);
        mGearState.setAction(GearEnum.REVERSE, ReverseAction);

        int v = 0;
        int s = -v;
        System.out.println(s);

        mGearState.switchToEcho();
        mGearState.switchToSport();
        mGearState.switchToEcho();
        mGearState.switchToReverse();
        mGearState.switchToEcho();
        mGearState.switchToParking();
    }

    private static GearAction ParkingAction = new GearAction() {
        @Override
        public void doSwitchToParking(int bg) {

        }

        @Override
        public void doSwitchToEcho(int bg) {
            System.out.println("doSwitchToEcho");
        }

        @Override
        public void doSwitchToSport(int bg) {

        }

        @Override
        public void doSwitchToReverse(int bg) {

        }
    };

    private static GearAction EchoAction = new GearAction() {
        @Override
        public void doSwitchToParking(int bg) {
            System.out.println("doSwitchToParking");

        }

        @Override
        public void doSwitchToEcho(int bg) {

        }

        @Override
        public void doSwitchToSport(int bg) {
            System.out.println("doSwitchToSport");

        }

        @Override
        public void doSwitchToReverse(int bg) {
            System.out.println("doSwitchToReverse");

        }
    };

    private static GearAction SportAction = new GearAction() {
        @Override
        public void doSwitchToParking(int bg) {

        }

        @Override
        public void doSwitchToEcho(int bg) {
            System.out.println("doSwitchToEcho");

        }

        @Override
        public void doSwitchToSport(int bg) {

        }

        @Override
        public void doSwitchToReverse(int bg) {

        }
    };

    private static GearAction ReverseAction = new GearAction() {
        @Override
        public void doSwitchToParking(int bg) {

        }

        @Override
        public void doSwitchToEcho(int bg) {
            System.out.println("doSwitchToEcho");

        }

        @Override
        public void doSwitchToSport(int bg) {

        }

        @Override
        public void doSwitchToReverse(int bg) {

        }
    };

}
