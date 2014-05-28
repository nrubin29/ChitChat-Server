package me.nrubin29.chitchat.server.handler;

import java.util.HashMap;

abstract class PacketHandler {

    public abstract void handle(HashMap<String, String> args);
}