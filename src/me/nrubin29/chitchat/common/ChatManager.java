package me.nrubin29.chitchat.common;

import me.nrubin29.chitchat.server.User;

import java.util.ArrayList;

public class ChatManager {

    private ChatManager() {
    }

    private static final ChatManager instance = new ChatManager();

    public static ChatManager getInstance() {
        return instance;
    }

    private final ArrayList<Chat> chats = new ArrayList<Chat>();
    private final ArrayList<User> users = new ArrayList<User>();

    public Chat addChat(Chat chat) {
        chats.add(chat);
        return chat;
    }

    public Chat removeChat(String name) {
        return removeChat(getChat(name));
    }

    Chat removeChat(Chat chat) {
        chats.remove(chat);
        return chat;
    }

    public Chat getChat(String name) {
        for (Chat chat : chats) {
            if (chat.getName().equals(name)) {
                return chat;
            }
        }

        return null;
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public User removeUser(User user) {
        users.remove(user);
        return user;
    }

    public User getUser(String name) {
        for (User user : getAllUsers()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public User[] getUsers(String... names) {
        User[] users = new User[names.length];

        int i = 0;
        for (String name : names) {
            users[i++] = getUser(name);
        }

        return users;
    }

    public User[] getAllUsers() {
        return users.toArray(new User[users.size()]);
    }
}