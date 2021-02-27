package com.yadea.launcher.stateGear;

public class ParkingGear extends GearBase{
    @Override
    public void switchToParking(int background) {
        if (getAction() != null)
            getAction().doSwitchToParking(background);
    }

    @Override
    public void swtichToEco(int background) {
        if (getAction() != null)
            getAction().doSwitchToEco(background);
    }

    @Override
    public void switchToReverse(int background) {
        if (getAction() != null)
            getAction().doSwitchToReverse(background);
    }

    @Override
    public void switchToSport(int background) {
        if (getAction() != null)
            getAction().doSwitchToSport(background);
    }

    @Override
    public void changeBackground(int background) {
        if(getAction() != null)
            getAction().doChangeBackground(background);
    }
}
