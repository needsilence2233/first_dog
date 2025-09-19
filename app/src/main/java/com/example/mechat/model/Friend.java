package com.example.mechat.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "friends")
public class Friend {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String friendName;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] friendAvatar;

    public Friend(String username, String friendName, byte[] friendAvatar) {
        this.username = username;
        this.friendName = friendName;
        this.friendAvatar = friendAvatar;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFriendName() { return friendName; }
    public void setFriendName(String friendName) { this.friendName = friendName; }

    public byte[] getFriendAvatar() { return friendAvatar; }
    public void setFriendAvatar(byte[] friendAvatar) { this.friendAvatar = friendAvatar; }
}
