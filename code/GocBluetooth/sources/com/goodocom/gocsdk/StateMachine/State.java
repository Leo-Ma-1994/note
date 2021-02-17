package com.goodocom.gocsdk.StateMachine;

import android.os.Message;

public class State implements IState {
    protected State() {
    }

    @Override // com.goodocom.gocsdk.StateMachine.IState
    public void enter() {
    }

    @Override // com.goodocom.gocsdk.StateMachine.IState
    public void exit() {
    }

    @Override // com.goodocom.gocsdk.StateMachine.IState
    public boolean processMessage(Message msg) {
        return false;
    }

    @Override // com.goodocom.gocsdk.StateMachine.IState
    public String getName() {
        String name = getClass().getName();
        return name.substring(name.lastIndexOf(36) + 1);
    }
}
