package me.nrubin29.chitchat.common;

import me.nrubin29.chitchat.server.MySQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Chat {

    private final String name;
    private final ArrayList<String> users;
    private final ArrayList<Message> messages;

    public Chat(ChatData chatData) {
        this(chatData.getName(), chatData.getUsers());
    }

    public Chat(String name, String... users) {
        this.name = name;
        this.users = new ArrayList<String>(Arrays.asList(users));
        this.messages = new ArrayList<Message>();
    }

    public String getName() {
        return name;
    }

    public String[] getUsers() {
        return users.toArray(new String[users.size()]);
    }

    public String getUsersWithCommas() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String user : users) {
            stringBuilder.append(user).append(",");
        }

        return stringBuilder.toString();
    }

    public void addUser(String user) {
        users.add(user);
        MySQL.getInstance().saveChat(this);
    }

    public Message[] getMessages() {
        return messages.toArray(new Message[messages.size()]);
    }

    public void addMessage(String sender, String chat, String msg, Date when) {
        Message message = new Message(sender, chat, msg, when);
        messages.add(message);
    }
}