package com.yadea.launcher.stateGear;

public abstract class GearBase {
    private GearAction mAction;
    public void setAction(GearAction gearAction){
        if(gearAction == null ){
            return;
        }
        mAction = gearAction;
    }

    public GearAction getAction() {
        return mAction;
    }

    public abstract void switchToParking(int background);
    public abstract void swtichToEco(int background);
    public abstract void switchToReverse(int background);
    public abstract void switchToSport(int background);
    public abstract void changeBackground(int background);
}
