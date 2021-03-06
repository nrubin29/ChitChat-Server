package me.nrubin29.chitchat.common.packet;

import me.nrubin29.chitchat.common.AbstractUser;

public class PacketUserStatusChange extends Packet {

    private static final long serialVersionUID = 5893694317968737139L;

    private String user;
    private String newStatus;

    public PacketUserStatusChange(AbstractUser user, AbstractUser.UserStatus newStatus) {
        this.user = user.getName();
        this.newStatus = newStatus.name();
    }

    public String getUser() {
        return user;
    }

    public String getNewStatus() {
        return newStatus;
    }
}