package me.nrubin29.chitchat.server.packethandler;

import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.PacketUserPasswordChange;
import me.nrubin29.chitchat.common.packet.PacketUserPasswordChangeResponse;
import me.nrubin29.chitchat.server.MySQL;

public class PacketUserPasswordChangeHandler extends PacketHandler<PacketUserPasswordChange> {

    PacketUserPasswordChangeHandler() {
        super(PacketUserPasswordChange.class);
    }

    @Override
    public void handle(PacketUserPasswordChange packet) {
        ChatManager.getInstance().getUser(packet.getUser()).sendPacket(new PacketUserPasswordChangeResponse(
                        packet.getUser(),
                        MySQL.getInstance().changePassword(packet.getUser(), packet.getOldPassword(), packet.getNewPassword()) ?
                                PacketUserPasswordChangeResponse.PasswordChangeResponse.SUCCESS :
                                PacketUserPasswordChangeResponse.PasswordChangeResponse.FAILURE
                )
        );
    }
}