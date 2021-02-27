package com.yadea.launcher.stateGear;

public interface GearAction {
    void doSwitchToParking(int bg);
    void doSwitchToEco(int bg);
    void doSwitchToSport(int bg);
    void doSwitchToReverse(int bg);
    void doChangeBackground(int bg);
}
