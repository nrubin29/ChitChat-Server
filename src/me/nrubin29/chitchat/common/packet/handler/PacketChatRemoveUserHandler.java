package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.packet.PacketChatRemoveUser;

public class PacketChatRemoveUserHandler extends PacketHandler<PacketChatRemoveUser> {

    public PacketChatRemoveUserHandler() {
        super(PacketChatRemoveUser.class);
    }

    @Override
    public void handle(PacketChatRemoveUser packet) {
        Chat chat = ChatManager.getInstance().getChat(packet.getChat());
        chat.removeUser(packet.getOldUser());

        for (String user : chat.getUsers()) {
            if (ChatManager.getInstance().getUser(user) != null && !user.equals(packet.getOldUser())) {
                ChatManager.getInstance().getUser(user).sendPacket(packet);
            }
        }
    }
}