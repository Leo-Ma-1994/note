

public abstract class GearBase {
    private GearAction mAction;

    protected void setAction(GearAction action) {
        if (action == null) return;
        mAction = action;
    }

    protected GearAction getAction() {
        return mAction;
    }

    abstract void switchToParking(int bg);
    abstract void switchToEcho(int bg);
    abstract void switchToSport(int bg);
    abstract void switchToReverse(int bg);


}
