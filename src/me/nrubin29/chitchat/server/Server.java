package me.nrubin29.chitchat.server;

import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatManager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.net.ServerSocket;
import java.util.Random;

class Server {

    private ServerSocket server;
    private SecretKey key;

    private Server() {
        try {
            MySQL.getInstance().setup();

            for (Chat chat : MySQL.getInstance().getAllChats()) {
                ChatManager.getInstance().addChat(chat);
            }

            do {
                Random r = new Random();
                int rPort = r.nextInt(65535);
                if (rPort > 1000) {
                    server = new ServerSocket(rPort, 100);
                }
            } while (server == null);

            System.out.println(server.getLocalPort());

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            this.key = kgen.generateKey();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            new User(server.accept(), key);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}