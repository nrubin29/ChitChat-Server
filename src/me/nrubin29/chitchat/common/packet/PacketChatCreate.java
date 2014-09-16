package me.nrubin29.chitchat.common.packet;

import me.nrubin29.chitchat.common.Chat;

public class PacketChatCreate extends Packet {

    private static final long serialVersionUID = -6536580021206781744L;

    private final String chat;
    private final String users;

    public PacketChatCreate(Chat chat) {
        this(chat.getName(), chat.getUsers());
    }

    public PacketChatCreate(String chat, String... users) {
        this.chat = chat;
        this.users = join(users);
    }

    public String getChat() {
        return chat;
    }

    public String getUsers() {
        return users;
    }

    private String join(String[] strs) {
        StringBuilder builder = new StringBuilder();

        for (String str : strs) {
            builder.append(str).append(",");
        }

        return builder.toString().trim();
    }
}