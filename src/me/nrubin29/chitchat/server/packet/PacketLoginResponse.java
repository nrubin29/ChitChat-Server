package me.nrubin29.chitchat.server.packet;

public class PacketLoginResponse extends Packet {

    public enum LoginResponse {
        SUCCESS,
        FAILURE
    }

    public PacketLoginResponse(LoginResponse response) {
        args.put("response", response.name());
    }
}