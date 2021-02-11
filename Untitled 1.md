```
if (msg.arg1 == CarProps.MOTOR_FAULT) {
    if (msg.arg2 == ConstantsApi.STATE_ON) {
        mStatusBarAdapter.addStatus(Status.MOTOR_FAULT);
    } else if (msg.arg2 == ConstantsApi.STATE_OFF) {
        mStatusBarAdapter.removeStatus(Status.MOTOR_FAULT);
    }
}
//HANDLE_FAULT 转把故障
if (msg.arg1 == CarProps.MCU_FAULT) {
    if (msg.arg2 == ConstantsApi.STATE_ON) {
        mStatusBarAdapter.addStatus(Status.HANDLE_FAULT);
    } else if (msg.arg2 == ConstantsApi.STATE_OFF) {
        mStatusBarAdapter.removeStatus(Status.HANDLE_FAULT);
    }
}
//ABS
//TODO::缺底层信号

//FIX 维修
if (msg.arg1 == CarProps.WRENCH_FAULT) {
    if (msg.arg2 == ConstantsApi.STATE_ON) {
        mStatusBarAdapter.addStatus(Status.FIX);
    } else if (msg.arg2 == ConstantsApi.STATE_OFF) {
        mStatusBarAdapter.removeStatus(Status.FIX);
    }
}

//HOT 电机过热
if (msg.arg1 == CarProps.ENGINE_FAULT) {
    if (msg.arg2 == ConstantsApi.STATE_ON) {
        mStatusBarAdapter.addStatus(Status.HOT);
    } else if (msg.arg2 == ConstantsApi.STATE_OFF) {
        mStatusBarAdapter.removeStatus(Status.HOT);
    }
}
```