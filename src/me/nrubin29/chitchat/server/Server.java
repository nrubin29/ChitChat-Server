package me.nrubin29.chitchat.server;

import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatManager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.net.ServerSocket;
import java.security.MessageDigest;
import java.util.Random;

class Server {

    private ServerSocket server;
    private SecretKey key;

    private Server() {
        try {
            MySQL.getInstance().setup();

            System.out.println("MySQL connected.");

            for (Chat chat : MySQL.getInstance().getAllChats()) {
                ChatManager.getInstance().addChat(chat);
            }

            System.out.println("Chats loaded.");

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

    public static String stringToSHA256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}