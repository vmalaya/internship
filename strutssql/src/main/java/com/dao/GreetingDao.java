package com.dao;

import com.controller.HelloAction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class GreetingDao {
    private static int counter = 0;

    public static int count() {
        counter = counter + 1;
        return counter;
    }

    public static Connection connection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/strutsdb", "root", "root");
    }

    public static int saveGreeting(HelloAction action) {
        int result = 0;
        try {
            Connection connection = connection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO greeting(message) values (?)")) {
                preparedStatement.setString(1, action.getMessage());
                result = preparedStatement.executeUpdate();
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        GreetingDao.counter = counter;
    }
}
