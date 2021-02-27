

public class EchoGear extends GearBase {

    @Override
    void switchToParking(int bg) {
        System.out.println("echo switch to parking");
        if (getAction() != null)
            getAction().doSwitchToParking(bg);

    }

    @Override
    void switchToEcho(int bg) {
        System.out.println("echo cant switch to echo");
        if (getAction() != null)
            getAction().doSwitchToEcho(bg);

    }

    @Override
    void switchToSport(int bg) {
        System.out.println("echo switch to sport");
        if (getAction() != null)
            getAction().doSwitchToSport(bg);

    }

    @Override
    void switchToReverse(int bg) {
        System.out.println("echo switch to reverse");
        if (getAction() != null)
            getAction().doSwitchToReverse(bg);

    }
}
