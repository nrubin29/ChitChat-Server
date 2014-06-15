package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.packet.packet.PacketLoginResponse;

public class PacketLoginResponseHandler extends PacketHandler<PacketLoginResponse> {

    public PacketLoginResponseHandler() {
        super(PacketLoginResponse.class);
    }

    @Override
    public void handle(PacketLoginResponse packet) {

    }
}