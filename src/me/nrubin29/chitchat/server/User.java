package me.nrubin29.chitchat.server;

import me.nrubin29.chitchat.common.AbstractUser;
import me.nrubin29.chitchat.common.ChatManager;
import me.nrubin29.chitchat.common.packet.handler.PacketHandlerManager;
import me.nrubin29.chitchat.common.packet.packet.*;
import me.nrubin29.chitchat.common.packet.packet.PacketRegisterResponse.RegisterResponse;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User extends AbstractUser {

    private SecretKey key;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public User(final Socket socket, final SecretKey key) {
        System.out.println("Got a request.");

        try {
            this.key = key;
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeObject(key);

            System.out.println("Wrote key.");

            Packet firstPacket = decryptPacket(inputStream.readObject());

            System.out.println("Got first packet: " + firstPacket);

            if (firstPacket instanceof PacketLoginRequest) {
                PacketLoginRequest packetRequest = (PacketLoginRequest) firstPacket;
                if (!MySQL.getInstance().validateLogin(packetRequest.getUser(), packetRequest.getPassword())) {
                    sendPacket(new PacketLoginResponse(packetRequest.getUser(), PacketLoginResponse.LoginResponse.FAILURE));
                    System.out.println("Request was denied.");
                    return;
                } else {
                    sendPacket(new PacketLoginResponse(packetRequest.getUser(), PacketLoginResponse.LoginResponse.SUCCESS));
                    setName(packetRequest.getUser());
                    ChatManager.getInstance().addUser(this);
                    System.out.println("Request was allowed in.");
                }
            } else if (firstPacket instanceof PacketRegisterRequest) {
                PacketRegisterRequest packetRequest = (PacketRegisterRequest) firstPacket;
                if (!MySQL.getInstance().validateRegister(packetRequest.getUser(), packetRequest.getPassword())) {
                    sendPacket(new PacketRegisterResponse(packetRequest.getUser(), RegisterResponse.FAILURE));
                    System.out.println("Request was denied.");
                    return;
                } else {
                    sendPacket(new PacketRegisterResponse(packetRequest.getUser(), RegisterResponse.SUCCESS));
                    setName(packetRequest.getUser());
                    ChatManager.getInstance().addUser(this);
                    System.out.println("Request was allowed in.");
                }
            }

            sendPacket(new PacketChatList(MySQL.getInstance().getChats(getName())));

            for (AbstractUser user : ChatManager.getInstance().getAllUsers()) {
                if (!user.equals(this)) {
                    ((User) user).sendPacket(new PacketUserJoin(this));
                }
            }

            sendPacket(new PacketUserList());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Packet packet = decryptPacket(inputStream.readObject());
                            System.out.println("Received packet: " + packet);
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
            System.out.println("Removing " + getName() + " because of " + e);
            ChatManager.getInstance().removeUser(this);
        }
    }

    public void sendPacket(Packet packet) {
        try {
            outputStream.writeObject(packet);
            System.out.println("Sent packet: " + packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Packet decryptPacket(Object o) {
        try {
            SealedObject so = (SealedObject) o;

            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);

            return (Packet) so.getObject(c);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}