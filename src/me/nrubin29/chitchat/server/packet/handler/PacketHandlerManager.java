package me.nrubin29.chitchat.server.packet.handler;

import me.nrubin29.chitchat.server.packet.packet.Packet;

import java.util.ArrayList;
import java.util.HashMap;

public class PacketHandlerManager {

    private static final PacketHandlerManager instance = new PacketHandlerManager();

    public static PacketHandlerManager getInstance() {
        return instance;
    }

    private final ArrayList<PacketHandler> packets = new ArrayList<PacketHandler>();

    private PacketHandlerManager() {
        packets.add(new PacketMessageHandler());
    }

    /*
     * PacketMessage chat:Friends user:Noah msg:Hello! when:0
     */
    public void handle(String unparsedPacket) {
        String[] unparsedArgs = unparsedPacket.split(" ");

        HashMap<String, String> args = new HashMap<String, String>();

        for (int i = 1; i < unparsedArgs.length; i++) {
            String[] splitArg = unparsedArgs[i].split(":");
            args.put(splitArg[0], splitArg[1]);
        }

        for (PacketHandler packet : packets) {
            if (packet.getClass().getName().equals("me.nrubin29.chitchat.client.packet.handler." + unparsedArgs[0] + "Handler")) {
                packet.handle(args);
            }
        }
    }

    public void handle(Packet p) {
        for (PacketHandler packet : packets) {
            if (packet.getClass().getName().equals("me.nrubin29.chitchat.client.packet.handler." + p.getClass().getSimpleName() + "Handler")) {
                packet.handle(p.args);
            }
        }
    }
}