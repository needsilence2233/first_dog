package com.example.mechat.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String username;
    private String password;
    private byte[] avatar;

    public User(String username, String password, byte[] avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public byte[] getAvatar() { return avatar; }
    public void setAvatar(byte[] avatar) { this.avatar = avatar; }
}