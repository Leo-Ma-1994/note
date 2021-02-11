package com.goodocom.gocsdk.event;

public class MusicPosEvent {
    public int current;
    public int total;

    public MusicPosEvent(int current2, int total2) {
        this.total = total2;
        this.current = current2;
    }
}
