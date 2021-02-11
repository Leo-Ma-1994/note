package com.goodocom.gocsdk.event;

public class MusicInfoEvent {
    public String album;
    public String artist;
    public int duration;
    public String name;
    public int pos;
    public int total;

    public MusicInfoEvent(String name2, String artist2, String album2, int duration2, int pos2, int total2) {
        this.name = name2;
        this.artist = artist2;
        this.album = album2;
        this.duration = duration2;
        this.pos = pos2;
        this.total = total2;
    }
}
