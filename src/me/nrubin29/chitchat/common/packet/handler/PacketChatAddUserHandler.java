package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.packet.PacketChatAddUser;
import me.nrubin29.chitchat.server.User;

public class PacketChatAddUserHandler extends PacketHandler<PacketChatAddUser> {

    public PacketChatAddUserHandler() {
        super(PacketChatAddUser.class);
    }

    @Override
    public void handle(PacketChatAddUser packet) {
        Chat chat = ChatManager.getInstance().getChat(packet.getChat());
        chat.addUser(packet.getNewUser());

        for (String user : chat.getUsers()) {
            if (ChatManager.getInstance().getUser(user) != null) {
                ((User) ChatManager.getInstance().getUser(user)).sendPacket(new PacketChatAddUser(packet.getChat(), packet.getNewUser()));
            }
        }
    }
}