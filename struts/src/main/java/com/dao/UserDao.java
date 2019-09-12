package com.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDao {
    private static Map<String, Integer> users = new ConcurrentHashMap<>();

    public static void save(String username) {
       if(users.containsKey(username)) users.replace(username, users.get(username)+1);
       else users.put(username, 1);
    }

    public static Map<String, Integer> getUsers() {
        return users;
    }

}
