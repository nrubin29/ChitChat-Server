package me.nrubin29.chitchat.server.packet.handler;

import java.util.HashMap;

public class PacketChatCreateHandler extends PacketHandler {

    @Override
    public void handle(HashMap<String, String> args) {
        String chat = args.get("chat");
        String[] users = args.get("users").split(",");
    }
}