// ICanServiceListener.aidl
package com.yadea.yadeaserver;

// Declare any non-default types here with import statements

interface ICanServiceListener {

    void onACCState(int value);
    void onCarState(int value);

    void onMetricBritish(int value);
    void onTimeDisplayState(int value);
    void onThemeChange(int value);
    void onThemeState(int value);

    void onGsmState(int value);
    void onGpsState(int value);
    void onBtState(int value);

    void onSmartKeyState(int value);
    void onIotDate(int year, int month, int day);
    void onIotTime(int hour, int minute, int second);

    void onBatteryWorkingState(int value);
    void onBatteryChargeState(int value);
    void onBatteryVoltage(int value);
    void onBatteryCurrent(int value);
    void onBatterySoc(int batteryId, int value);
    void onBatterySocLow(int batteryId, int value);

    void onIotVersion(String value);
    void onBmsVersion(int batteryId, String value);
    void onMcuVersion(String value);

    void onLeftLED(int value);
    void onRightLED(int value);
    void onLightState(int lightId, int value);
    void onFaultState(int faultId, int value);
    void onSeatSense(int value);
    void onElecSeatLock(int value);
    void onElecTailBoxLock(int value);
    void onCruiseMode(int value);

    void onSpeed(int value);

    void onSingleDrive(int value);
    void onTotalDrive(int value);
    void onAlarmState(int value);
    void onGearState(int value);

    void onBraceSignal(int value);

    void onBacklightLevel(int value);
    void onBacklightLevelFeedback(int value);

    void onSilent(int value);

    void onIotFault(int value);
    void onBmsFault(int batteryId, int value);

    // TODO: Not used
    void onBatteryChargeRemainTime(int batteryId, int value);
    void onBcmCount(int value);
    void onLightSensor(int value);
    void onMotorRealSpeed(int value);
    void onExtraKey(int value);
    void onKeyOutput(int value);

}