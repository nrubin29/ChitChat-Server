package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.packet.PacketMessage;

public class PacketMessageHandler extends PacketHandler<PacketMessage> {

    public PacketMessageHandler() {
        super(PacketMessage.class);
    }

    @Override
    public void handle(PacketMessage packet) {
        Chat chat = ChatManager.getInstance().getChat(packet.getChat());
        chat.addMessage(packet.getSender(), packet.getChat(), packet.getMessage().replaceAll("%20", " "), packet.getWhen());

        for (String u : chat.getUsers()) {
            if (ChatManager.getInstance().getUser(u) != null) {
                ChatManager.getInstance().getUser(u).sendPacket(packet);
            }
        }
    }
}