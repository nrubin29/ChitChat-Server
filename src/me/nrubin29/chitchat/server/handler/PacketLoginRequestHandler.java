package me.nrubin29.chitchat.server.handler;

import java.util.HashMap;

public class PacketLoginRequestHandler extends PacketHandler {

    @Override
    public void handle(HashMap<String, String> args) {
        String user = args.get("user");
        String password = args.get("password");
    }
}