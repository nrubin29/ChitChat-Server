package me.nrubin29.chitchat.server.handler;

import me.nrubin29.chitchat.server.Chat;
import me.nrubin29.chitchat.server.ChatManager;

import java.util.HashMap;

public class PacketChatCreateHandler extends PacketHandler {

    @Override
    public void handle(HashMap<String, String> args) {
        String chat = args.get("chat");
        String[] users = args.get("users").split(",");

        ChatManager.getInstance().addChat(new Chat(chat, users));
    }
}