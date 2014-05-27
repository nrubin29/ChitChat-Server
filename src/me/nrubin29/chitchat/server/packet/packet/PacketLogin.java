package me.nrubin29.chitchat.server.packet.packet;

class PacketLogin extends Packet {

    public PacketLogin(String user, String password) {
        args.put("user", user);
        args.put("password", password);
    }
}