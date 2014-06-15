package me.nrubin29.chitchat.common.packet.handler;

import me.nrubin29.chitchat.common.packet.packet.PacketRegisterRequest;

public class PacketRegisterResponseHandler extends PacketHandler<PacketRegisterRequest> {

    public PacketRegisterResponseHandler() {
        super(PacketRegisterRequest.class);
    }

    @Override
    public void handle(PacketRegisterRequest packet) {

    }
}