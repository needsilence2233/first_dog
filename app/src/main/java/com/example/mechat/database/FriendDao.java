package com.example.mechat.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mechat.model.Friend;
import java.util.List;

@Dao
public interface FriendDao {
    @Insert
    void insert(Friend friend);

    @Query("SELECT * FROM friends WHERE username = :username")
    List<Friend> getFriendsByUser(String username);

    @Query("SELECT * FROM friends")
    List<Friend> getAllFriends();

    @Query("SELECT * FROM friends WHERE friendName = :friendName LIMIT 1")
    Friend getFriendByName(String friendName);
}