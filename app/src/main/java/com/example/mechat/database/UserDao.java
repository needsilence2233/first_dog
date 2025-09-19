package com.example.mechat.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mechat.model.User;
import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUser(String username);

    @Query("SELECT COUNT(*) FROM users WHERE username = :username AND password = :password")
    int validateUser(String username, String password);

    @Query("UPDATE users SET password = :newPassword WHERE username = :username AND password = :oldPassword")
    int updatePassword(String username, String oldPassword, String newPassword);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}