package me.nrubin29.chitchat.server.handler;

import me.nrubin29.chitchat.server.packet.PacketLoginResponse;

import java.util.HashMap;

public class PacketLoginResponseHandler extends PacketHandler {

    @Override
    public void handle(HashMap<String, String> args) {
        PacketLoginResponse.LoginResponse response = PacketLoginResponse.LoginResponse.valueOf(args.get("response"));
    }
}