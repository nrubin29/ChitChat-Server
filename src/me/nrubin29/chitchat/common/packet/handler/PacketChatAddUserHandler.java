package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.packet.PacketChatAddUser;

public class PacketChatAddUserHandler extends PacketHandler<PacketChatAddUser> {

    public PacketChatAddUserHandler() {
        super(PacketChatAddUser.class);
    }

    @Override
    public void handle(PacketChatAddUser packet) {
        ChatManager.getInstance().getChat(packet.getChat()).addUser(ChatManager.getInstance().getUser(packet.getNewUser()));
    }
}