package me.nrubin29.chitchat.server.packethandler;

import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.PacketUserDisplayNameChange;
import me.nrubin29.chitchat.server.MySQL;
import me.nrubin29.chitchat.server.User;

public class PacketUserDisplayNameChangeHandler extends PacketHandler<PacketUserDisplayNameChange> {

    PacketUserDisplayNameChangeHandler() {
        super(PacketUserDisplayNameChange.class);
    }

    @Override
    public void handle(PacketUserDisplayNameChange packet) {
        MySQL.getInstance().changeDisplayName(packet.getUser(), packet.getNewDisplayName());

        for (User user : ChatManager.getInstance().getAllUsers()) {
            user.sendPacket(packet);
        }
    }
}