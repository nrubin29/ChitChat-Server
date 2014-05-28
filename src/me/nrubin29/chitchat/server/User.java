package me.nrubin29.chitchat.server;

import me.nrubin29.chitchat.server.handler.PacketHandlerManager;
import me.nrubin29.chitchat.server.packet.Packet;
import me.nrubin29.chitchat.server.packet.PacketLoginRequest;
import me.nrubin29.chitchat.server.packet.PacketLoginResponse;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {

    private SecretKey key;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private String name;

    public User(final Socket socket, final SecretKey key) {
        System.out.println("Got a request.");

        try {
            this.key = key;
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeObject(key);

            System.out.println("Wrote key.");

            Packet firstPacket = (Packet) ((SealedObject) inputStream.readObject()).getObject(key, "AES");

            System.out.println("Got first packet: " + firstPacket);

            if (firstPacket instanceof PacketLoginRequest) {
                if (!firstPacket.args.get("username").equals("Noah")) {
                    sendPacket(new PacketLoginResponse(PacketLoginResponse.LoginResponse.FAILURE));
                    System.out.println("Request was denied.");
                    return;
                } else {
                    sendPacket(new PacketLoginResponse(PacketLoginResponse.LoginResponse.SUCCESS));
                    name = firstPacket.args.get("username"); // TODO: Eventually, I'll poll a file (or database) for the info.
                    System.out.println("Request was allowed in.");
                }
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
//                            Packet packet = (Packet) ((SealedObject) inputStream.readObject()).getObject(key);
                            Packet packet = (Packet) inputStream.readObject();
                            PacketHandlerManager.getInstance().handle(packet);
                        } catch (EOFException e) {
                            System.out.println("Lost connection to client.");

                            try {
                                socket.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            ChatManager.getInstance().removeUser(User.this);

                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            ChatManager.getInstance().removeUser(this);
        }
    }

    public void sendPacket(Packet packet) {
        try {
//            Cipher c = Cipher.getInstance("AES");
//            c.init(Cipher.ENCRYPT_MODE, key);
//            outputStream.writeObject(new SealedObject(packet, c));
            outputStream.writeObject(packet);
            System.out.println("Sent packet: " + packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void handlePacket(Packet packet) {
        PacketHandlerManager.getInstance().handle(packet);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}