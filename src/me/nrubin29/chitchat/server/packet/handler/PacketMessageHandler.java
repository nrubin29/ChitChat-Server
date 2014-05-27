package me.nrubin29.chitchat.server.packet.handler;

import java.util.HashMap;

public class PacketMessageHandler extends PacketHandler {

    @Override
    public void handle(HashMap<String, String> args) {
        String chat = args.get("chat");
        String sender = args.get("sender");
        String msg = args.get("msg").replace("%20", " ");
        long when = Long.valueOf(args.get("when"));
    }
}