package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.packet.PacketChatAddUser;
import me.nrubin29.chitchat.common.packet.packet.PacketChatCreate;

public class PacketChatAddUserHandler extends PacketHandler<PacketChatAddUser> {

    public PacketChatAddUserHandler() {
        super(PacketChatAddUser.class);
    }

    @Override
    public void handle(PacketChatAddUser packet) {
        Chat chat = ChatManager.getInstance().getChat(packet.getChat());
        chat.addUser(packet.getNewUser());

        if (ChatManager.getInstance().getUser(packet.getNewUser()) != null) {
            ChatManager.getInstance().getUser(packet.getNewUser()).sendPacket(new PacketChatCreate(chat));
        }

        for (String user : chat.getUsers()) {
            if (ChatManager.getInstance().getUser(user) != null) {
                ChatManager.getInstance().getUser(user).sendPacket(packet);
            }
        }
    }
}