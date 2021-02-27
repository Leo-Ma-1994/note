package com.yadea.launcher.stateGear;

import android.os.Looper;

import java.security.PublicKey;

public class StateContext {
    public GearBase PARKING_GEAR = new ParkingGear();
    public GearBase ECO_GEAR = new EcoGear();
    public GearBase SPORT_GEAR = new SportGear();
    public GearBase REVERSE_GEAR = new ReverseGear();

    public static final int PARKING = 1;
    public static final int ECO = 2;
    public static final int SPORT = 3;
    public static final int REVERSE = 4;

    /**
     * 与背景相关的变量及状态
     */
    private int background = DEFAULT;
    private boolean mLocked = false;
    private boolean mSeated = false;
    public static final int DEFAULT = 1; // 正常背景
    public static final int SEAT = 2; //坐垫感应到的背景
    public static final int LOCK = 3; //坐垫未锁的背景

    public boolean isLocked() {
        return mLocked;
    }

    public void setLocked(boolean mLocked) {
        this.mLocked = mLocked;
    }

    public boolean isSeated() {
        return mSeated;
    }

    public void setSeated(boolean mSeated) {
        this.mSeated = mSeated;
    }

    private int getBackground(){
        if(mLocked){
            if(mSeated){
                background = DEFAULT;
            }
            else {
                background = SEAT;
            }
        }
        else{
            background = LOCK;
        }
        return background;
    }

    /**
     * 当前状态. 默认初始状态为Parking
     */
    private GearBase mGear = PARKING_GEAR;

    public int getGear() {
        if (PARKING_GEAR.equals(mGear)) {
            return PARKING;
        } else if (ECO_GEAR.equals(mGear)) {
            return ECO;
        } else if (SPORT_GEAR.equals(mGear)) {
            return SPORT;
        } else if (REVERSE_GEAR.equals(mGear)) {
            return REVERSE;
        }
        return 0;
    }

    public void switchToParking(){
        mGear.switchToParking(getBackground());
        mGear = PARKING_GEAR;
    }

    public void switchToECO(){
        mGear.switchToParking(getBackground());
        mGear = ECO_GEAR;
    }

    public void switchToSport(){
        mGear.switchToSport(getBackground());
        mGear = SPORT_GEAR;
    }

    public void switchToReverse(){
        mGear.switchToReverse(getBackground());
        mGear = REVERSE_GEAR;
    }

    public void changeBackground(){
        mGear.changeBackground(getBackground());

    }

    public void setAction(int gear, GearAction action){
        if(action == null){
            return;
        }
        switch (gear){
            case PARKING:PARKING_GEAR.setAction(action); break;
            case ECO:ECO_GEAR.setAction(action); break;
            case SPORT:SPORT_GEAR.setAction(action); break;
            case REVERSE:REVERSE_GEAR.setAction(action); break;
        }
    }

}
