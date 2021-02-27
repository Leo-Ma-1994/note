package com.yadea.launcher.service;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import com.yadea.launcher.api.CarProps;
import com.yadea.launcher.util.LogUtils;
import com.yadea.yadeaserver.ICanServiceListener;

import java.util.ArrayList;
import java.util.List;

public class CanServiceListener extends ICanServiceListener.Stub {
    private static final String TAG = CanServiceListener.class.getSimpleName();

    private List<Handler> mHandlers = new ArrayList<>();

    private void sendMessage(int propId, int val) {
        Message msg = Message.obtain();
        Message msgCopy;
        msg.what = propId;
        msg.arg1 = val;
        for (Handler handler : mHandlers) {
            msgCopy = Message.obtain(msg);
            handler.sendMessage(msgCopy);
        }
    }

    private void sendMessage(int propId, int val1, int val2) {
        Message msg = Message.obtain();
        Message msgCopy;
        msg.what = propId;
        msg.arg1 = val1;
        msg.arg2 = val2;
        for (Handler handler : mHandlers) {
            msgCopy = Message.obtain(msg);
            handler.sendMessage(msgCopy);
        }
    }

    private void sendMessage(int propId, String val) {
        Message msg = Message.obtain();
        Message msgCopy;
        msg.what = propId;
        msg.obj = val;
        for (Handler handler : mHandlers) {
            msgCopy = Message.obtain(msg);
            handler.sendMessage(msgCopy);
        }
    }

//********************              Public method                          ********************//
    public void addHandler(Handler handler) {
        if (null != handler && !mHandlers.contains(handler)) {
            mHandlers.add(handler);
        } else {
            LogUtils.w(TAG, "handler is null or has already in use");
        }
    }

    public void removeHandler(Handler handler) {
        if (null != handler && mHandlers.contains(handler)) {
            mHandlers.remove(handler);
        } else {
            LogUtils.w(TAG, "handler is null or not in use");
        }
    }

//********************              Implement method                          ********************//
    @Override
    public void onACCState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.ACC_STATE, value);
    }

    @Override
    public void onCarState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.CAR_STATE, value);
    }

    @Override
    public void onMetricBritish(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.METRIC_BRITISH, value);
    }

    @Override
    public void onTimeDisplayState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.TIME_DISPLAY, value);
    }

    @Override
    public void onThemeChange(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.THEME, value);
    }

    @Override
    public void onThemeState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.INSTRUMENT_THEME_STATE, value);
    }

    @Override
    public void onGsmState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.GSM_STATE, value);
    }

    @Override
    public void onGpsState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.GPS_STATE, value);
    }

    @Override
    public void onBtState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.BT_STATE, value);
    }

    @Override
    public void onSmartKeyState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.SMARTKEY_STATE, value);
    }

    @Override
    public void onIotDate(int year, int month, int day) throws RemoteException {
        String date = year + "-" + month + "-" + day;
        LogUtils.v(TAG, date);
        sendMessage(CarProps.IOT_YEAR, date);
    }

    @Override
    public void onIotTime(int hour, int minute, int second) throws RemoteException {
        String time = hour + ":" + minute + ":" + second;
        LogUtils.v(TAG, time);
        sendMessage(CarProps.IOT_HOUR, time);
    }

    @Override
    public void onBatteryWorkingState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.BATTERY_STATE, value);
    }

    @Override
    public void onBatteryChargeState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.BATTERY_CHARGE_STATE, value);
    }

    @Override
    public void onBatteryVoltage(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.ALL_BATTERY_VOL, value);
    }

    @Override
    public void onBatteryCurrent(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.ALL_BATTERY_CUR, value);
    }

    @Override
    public void onBatterySoc(int batteryId, int value) throws RemoteException {
        LogUtils.v(TAG, "batteryId" + batteryId + "value: " + value);
        sendMessage(CarProps.MAIN_BATTERY_SOC, value);
    }

    @Override
    public void onBatterySocLow(int batteryId, int value) throws RemoteException {
        LogUtils.v(TAG, "batteryId" + batteryId + "value: " + value);
        sendMessage(CarProps.MAIN_BATTERY_SOC_LOW, value);
    }

    @Override
    public void onIotVersion(String value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.IOT_MAIN_VERSION, value);
    }

    @Override
    public void onBmsVersion(int batteryId, String value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.BMS0_MAIN_VERSION, value);
    }

    @Override
    public void onMcuVersion(String value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.MCU_MAIN_VERSION, value);
    }

    @Override
    public void onLeftLED(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.LEFT_LED, value);
    }

    @Override
    public void onRightLED(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.RIGHT_LED, value);
    }

    @Override
    public void onLightState(int lightId, int value) throws RemoteException {
        LogUtils.v(TAG,  "lightId: " + lightId + "value: " + value);
        sendMessage(CarProps.HEAD_LIGHT, value);
    }

    @Override
    public void onFaultState(int faultId, int value) throws RemoteException {
        LogUtils.v(TAG, "faultId: " + faultId + "value: " + value);
        sendMessage(CarProps.WRENCH_FAULT, faultId, value);
    }

    @Override
    public void onSeatSense(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.SEAT_SENSE, value);
    }

    @Override
    public void onElecSeatLock(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.ELEC_SEAT_LOCK, value);
    }

    @Override
    public void onElecTailBoxLock(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.ELEC_TAILGATE_LOCK, value);
    }

    @Override
    public void onCruiseMode(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.CRUISE_MODE, value);
    }

    @Override
    public void onSpeed(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.SPEED_VALUE, value);
    }

    @Override
    public void onSingleDrive(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.TRIP_VALUE, value);
    }

    @Override
    public void onTotalDrive(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.ODO_VALUE, value);
    }

    @Override
    public void onAlarmState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.ALARM_STATE, value);
    }

    @Override
    public void onGearState(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.PARKING_GEAR, value);
    }

    @Override
    public void onBraceSignal(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.BRACE_SIGNAL, value);
    }

    @Override
    public void onBacklightLevel(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.BACKLIGHT_LEVEL, value);
    }

    @Override
    public void onBacklightLevelFeedback(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.BACKLIGHT_LEVEL_FEEDBACK, value);
    }

    @Override
    public void onSilent(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.SILENT, value);
    }

    @Override
    public void onIotFault(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.IOT_FAULT, value);
    }

    @Override
    public void onBmsFault(int batteryId, int value) throws RemoteException {
        LogUtils.v(TAG, "batteryId: " + batteryId + "value: " + value);
        sendMessage(CarProps.BMS0_FAULT, value);
    }

    @Override
    public void onBatteryChargeRemainTime(int batteryId, int value) throws RemoteException {
        LogUtils.v(TAG, "batteryId: " + batteryId + "value: " + value);
        sendMessage(CarProps.ALL_BATTERY_TTF, value);
    }

    @Override
    public void onBcmCount(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.BCM_COUNT, value);
    }

    @Override
    public void onLightSensor(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.LIGHT_SENSOR, value);
    }

    @Override
    public void onMotorRealSpeed(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.MOTOR_ACTUAL_SPEED, value);
    }

    @Override
    public void onExtraKey(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.EXT_KEY, value);
    }

    @Override
    public void onKeyOutput(int value) throws RemoteException {
        LogUtils.v(TAG, "value: " + value);
        sendMessage(CarProps.KEY_OUTPUT, value);
    }
}
