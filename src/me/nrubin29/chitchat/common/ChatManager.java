package me.nrubin29.chitchat.common;

import java.util.ArrayList;

public class ChatManager {

    private ChatManager() {
    }

    private static final ChatManager instance = new ChatManager();

    public static ChatManager getInstance() {
        return instance;
    }

    private final ArrayList<Chat> chats = new ArrayList<Chat>();
    private final ArrayList<AbstractUser> users = new ArrayList<AbstractUser>();

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

    public AbstractUser addUser(AbstractUser user) {
        users.add(user);
        return user;
    }

    public AbstractUser removeUser(AbstractUser user) {
        users.remove(user);
        return user;
    }

    public AbstractUser getUser(String name) {
        for (AbstractUser user : getAllUsers()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public AbstractUser[] getUsers(String... names) {
        AbstractUser[] users = new AbstractUser[names.length];

        int i = 0;
        for (String name : names) {
            users[i++] = getUser(name);
        }

        return users;
    }

    public AbstractUser[] getAllUsers() {
        return users.toArray(new AbstractUser[users.size()]);
    }
}