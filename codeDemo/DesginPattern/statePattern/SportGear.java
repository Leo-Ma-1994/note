

public class SportGear extends GearBase {
    @Override
    void switchToParking(int bg) {
        System.out.println("sport cant switch to parking");
        if (getAction() != null)
            getAction().doSwitchToReverse(bg);

    }

    @Override
    void switchToEcho(int bg) {
        System.out.println("sport switch to echo");
        if (getAction() != null)
            getAction().doSwitchToEcho(bg);

    }

    @Override
    void switchToSport(int bg) {
        System.out.println("sport cant switch to sport");
        if (getAction() != null)
            getAction().doSwitchToSport(bg);

    }

    @Override
    void switchToReverse(int bg) {
        System.out.println("sport switch to reverse");
        if (getAction() != null)
            getAction().doSwitchToReverse(bg);

    }
}
