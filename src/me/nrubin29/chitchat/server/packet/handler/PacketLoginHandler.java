package me.nrubin29.chitchat.server.packet.handler;

import java.util.HashMap;

public class PacketLoginHandler extends PacketHandler {

    @Override
    public void handle(HashMap<String, String> args) {
        String user = args.get("user");
        String password = args.get("password");
    }
}