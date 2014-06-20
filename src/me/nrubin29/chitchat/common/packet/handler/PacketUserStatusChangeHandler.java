package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.AbstractUser;
import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.packet.PacketUserStatusChange;
import me.nrubin29.chitchat.server.User;

public class PacketUserStatusChangeHandler extends PacketHandler<PacketUserStatusChange> {

    PacketUserStatusChangeHandler() {
        super(PacketUserStatusChange.class);
    }

    @Override
    public void handle(PacketUserStatusChange packet) {
        User user = ChatManager.getInstance().getUser(packet.getUser());
        user.setUserStatus(AbstractUser.UserStatus.valueOf(packet.getNewStatus()));

        for (Chat chat : ChatManager.getInstance().getChats(user)) {
            for (User otherUser : ChatManager.getInstance().getUsers(chat.getUsers())) {
                if (otherUser != null) {
                    otherUser.sendPacket(packet);
                }
            }
        }
    }
}