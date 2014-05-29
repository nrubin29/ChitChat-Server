package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.AbstractUser;
import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.packet.PacketMessage;
import me.nrubin29.chitchat.server.User;

public class PacketMessageHandler extends PacketHandler<PacketMessage> {

    public PacketMessageHandler() {
        super(PacketMessage.class);
    }

    @Override
    public void handle(PacketMessage packet) {
        Chat chat = ChatManager.getInstance().getChat(packet.getChat());
        chat.addMessage(packet.getSender(), packet.getChat(), packet.getMessage().replaceAll("%20", " "), packet.getWhen());

        for (AbstractUser u : chat.getUsers()) {
            User user = (User) u;
            user.sendPacket(packet);
        }
    }
}