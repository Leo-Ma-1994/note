package com.goodocom.gocsdk.event;

public class MessageListEvent {
    public String handle;
    public String name;
    public String num;
    public boolean read;
    public String time;
    public String title;

    public MessageListEvent(String handle2, boolean read2, String time2, String name2, String num2, String title2) {
        this.handle = handle2;
        this.read = read2;
        this.time = time2;
        this.name = name2;
        this.num = num2;
        this.title = title2;
    }
}
