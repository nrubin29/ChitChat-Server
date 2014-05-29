package me.nrubin29.chitchat.server;

import java.sql.*;

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
            connection = DriverManager.getConnection("sensitive-information");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public int[] getHighScores() throws SQLException {
//    	int[] highScores = new int[10];
//    	
//    	if (!setup) {
//            setup();
//        }
//
//    	PreparedStatement statement = connection.prepareStatement("select realScore from scores14 where version='" + UpdateChecker.VERSION + "' order by realScore desc;");
//        ResultSet results = statement.executeQuery();
//        
//        for (int i = 0; i < 10; i++) {
//        	if (results.next()) highScores[i] = results.getInt(1);
//        }
//        
//        statement.close();
//    	
//    	return highScores;
//    }
//
//    public void pushRound(final Round round) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (!setup) {
//                    setup();
//                }
//
//                try {
//                    PreparedStatement statement = connection.prepareStatement(
//                            "insert into scores14 (realScore, boostedScore, startScore, secondsPlayed, startTime, version)\n" + "values(" +
//                                    "'" + round.getRealScore() + "','" +
//                                    round.getBoostedScore() + "','" +
//                                    round.getStartScore() + "','" +
//                                    round.getSecondsPlayed() + "','" +
//                                    round.getStartTime().toString() + "','" +
//                                    UpdateChecker.VERSION + "')"
//                    );
//                    statement.executeUpdate();
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    public void pushComment(final String comment) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (!setup) {
//                    setup();
//                }
//
//                try {
//                    PreparedStatement statement = connection.prepareStatement(
//                            "insert into feedback (comment, timeSubmitted, version)\nvalues (" +
//                                    "'" + comment + "','" +
//                                    new Date() + "','" +
//                                    UpdateChecker.VERSION + "')"
//                    );
//                    statement.executeUpdate();
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    private boolean success;

    public boolean validateLogin(final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = connection.prepareStatement(
                            "select password from users where username='" + username + "';"
                    );
                    ResultSet results = statement.executeQuery();
                    results.next();
                    String remotePassword = results.getString("password");

                    if (remotePassword.equals(password)) {
                        success = true;
                    }

                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        boolean localSuccess = success;
        success = false;
        return localSuccess;
    }
}