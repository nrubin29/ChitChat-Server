package me.nrubin29.chitchat.server;

import me.nrubin29.chitchat.common.Chat;
import me.nrubin29.chitchat.common.ChatData;

import java.sql.*;
import java.util.ArrayList;

public class MySQL {

    private MySQL() {
    }

    private static final MySQL instance = new MySQL();

    public static MySQL getInstance() {
        return instance;
    }

    private Connection connection;

    public void setup() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            /*
            TODO: Don't forget to remove this on push.
             */

            connection = DriverManager.getConnection(MySQLInfo.CONNECTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateLogin(final String username, final String password) {
        boolean loginSuccess = false;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select password from users where username='" + username + "';"
            );
            ResultSet results = statement.executeQuery();
            results.next();
            String remotePassword = results.getString("password");

            if (remotePassword.equals(password)) {
                loginSuccess = true;
            }

            statement.close();
        } catch (SQLException e) {
            loginSuccess = false;
        }

        return loginSuccess;
    }

    public boolean validateRegister(final String username, final String password) {
        boolean registerSuccess = false;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select username from users where username='" + username + "';"
            );

            ResultSet results = statement.executeQuery();

            if (results.first()) {
                registerSuccess = false;
                System.out.println("Found a result.");
            } else {
                PreparedStatement statement2 = connection.prepareStatement(
                        "insert into users (username, password) values ('" + username + "', '" + password + "');"
                );

                statement2.executeUpdate();
                statement2.close();

                registerSuccess = true;
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registerSuccess;
    }

    public void saveChat(final Chat chat) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select name from chats where name='" + chat.getName() + "';"
            );

            ResultSet results = statement.executeQuery();

            if (results.first()) {
                PreparedStatement statement1 = connection.prepareStatement(
                        "update chats set users='" + chat.getUsersWithCommas() + "' where name='" + chat.getName() + "';"
                );

                statement1.executeUpdate();
                statement1.close();
            } else {
                PreparedStatement statement1 = connection.prepareStatement(
                        "insert into chats (name, users) values ('" + chat.getName() + "', '" + chat.getUsersWithCommas() + "');"
                );

                statement1.executeUpdate();
                statement1.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChatData[] getChats(final String user) {
        ArrayList<ChatData> chats = new ArrayList<ChatData>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select name, users from chats where users like '%" + user + "%';"
            );

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String name = results.getString("name");
                String users = results.getString("users");
                System.out.println("Found chat " + name + " for users " + users);
                chats.add(new ChatData(name, users.split(",")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ChatData[] chatsArray = chats.toArray(new ChatData[chats.size()]);
        chats.clear();
        return chatsArray;
    }

    public Chat[] getAllChats() {
        ArrayList<Chat> allChats = new ArrayList<Chat>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select name, users from chats;"
            );

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String name = results.getString("name");
                String users = results.getString("users");
                allChats.add(new Chat(name, users.split(",")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Chat[] chatsArray = allChats.toArray(new Chat[allChats.size()]);
        allChats.clear();
        return chatsArray;
    }
}