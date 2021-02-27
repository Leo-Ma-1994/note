
public class GearState {

    private GearBase PARKING = new ParkingGear();
    private GearBase ECHO = new EchoGear();
    private GearBase SPORT = new SportGear();
    private GearBase REVERSE = new ReverseGear();

    private GearBase mGear = PARKING;

    private boolean mLocked = false;
    private boolean mSeated = false;

    public static final int DEFAULT = 1;
    public static final int SEAT = 2;
    public static final int LOCK = 3;

    private int getBackground() {
        int result = DEFAULT;
        if (mLocked) {
            if (mSeated) {
                result = DEFAULT;
            } else {
                result = SEAT;
            }
        } else {
            result = LOCK;
        }
        return result;
    }

    public void switchToParking() {
        mGear.switchToParking(getBackground());
        mGear = PARKING;
    }

    public void switchToEcho() {
        mGear.switchToEcho(getBackground());
        mGear = ECHO;
    }

    public void switchToSport() {
        mGear.switchToSport(getBackground());
        mGear = SPORT;
    }

    public void switchToReverse() {
        mGear.switchToReverse(getBackground());
        mGear = REVERSE;
    }

    public void setAction(GearEnum gear, GearAction action) {
        if (action == null) return;
        switch (gear) {
            case PARKING: PARKING.setAction(action); break;
            case ECHO: ECHO.setAction(action); break;
            case SPORT: SPORT.setAction(action); break;
            case REVERSE: REVERSE.setAction(action); break;
            default: break;
        }
    }
}
