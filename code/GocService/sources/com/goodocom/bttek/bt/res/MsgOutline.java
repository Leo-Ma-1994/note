package com.goodocom.bttek.bt.res;

import java.io.Serializable;

public class MsgOutline implements Serializable {
    private static final long serialVersionUID = 1;
    private int _id;
    private String attachment_size;
    private String datetime;
    private String folder;
    private String handle;
    private String macAddress;
    private String message;
    private String priority;
    private String protect;
    private String read;
    private String reception_status;
    private String recipient_addressing;
    private String recipient_name;
    private String replyto_addressing;
    private String sender_addressing;
    private String sender_name;
    private String sent;
    private String size;
    private String subject;
    private String text;
    private String type;

    public int get_id() {
        return this._id;
    }

    public void set_id(int _id2) {
        this._id = _id2;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String macAddress2) {
        this.macAddress = macAddress2;
    }

    public String getHandle() {
        return this.handle;
    }

    public void setHandle(String handle2) {
        this.handle = handle2;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject2) {
        this.subject = subject2;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime2) {
        this.datetime = datetime2;
    }

    public String getSender_name() {
        return this.sender_name;
    }

    public void setSender_name(String sender_name2) {
        this.sender_name = sender_name2;
    }

    public String getSender_addressing() {
        return this.sender_addressing;
    }

    public void setSender_addressing(String sender_addressing2) {
        this.sender_addressing = sender_addressing2;
    }

    public String getReplyto_addressing() {
        return this.replyto_addressing;
    }

    public void setReplyto_addressing(String replyto_addressing2) {
        this.replyto_addressing = replyto_addressing2;
    }

    public String getRecipient_name() {
        return this.recipient_name;
    }

    public void setRecipient_name(String recipient_name2) {
        this.recipient_name = recipient_name2;
    }

    public String getRecipient_addressing() {
        return this.recipient_addressing;
    }

    public void setRecipient_addressing(String recipient_addressing2) {
        this.recipient_addressing = recipient_addressing2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size2) {
        this.size = size2;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text2) {
        this.text = text2;
    }

    public String getReception_status() {
        return this.reception_status;
    }

    public void setReception_status(String reception_status2) {
        this.reception_status = reception_status2;
    }

    public String getAttachment_size() {
        return this.attachment_size;
    }

    public void setAttachment_size(String attachment_size2) {
        this.attachment_size = attachment_size2;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority2) {
        this.priority = priority2;
    }

    public String getRead() {
        return this.read;
    }

    public void setRead(String read2) {
        this.read = read2;
    }

    public String getSent() {
        return this.sent;
    }

    public void setSent(String sent2) {
        this.sent = sent2;
    }

    public String getProtect() {
        return this.protect;
    }

    public void setProtect(String protect2) {
        this.protect = protect2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String folder2) {
        this.folder = folder2;
    }
}
