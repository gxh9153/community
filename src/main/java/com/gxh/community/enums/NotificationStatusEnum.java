package com.gxh.community.enums;

public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);
    private int type;

    public int getType() {
        return type;
    }

    NotificationStatusEnum(int type) {
        this.type = type;
    }
}
