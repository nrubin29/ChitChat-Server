package me.nrubin29.chitchat.server.packet.handler;

import java.util.HashMap;

abstract class PacketHandler {

    public abstract void handle(HashMap<String, String> args);
}