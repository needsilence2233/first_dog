package com.example.mechat.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mechat.model.Message;
import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void insert(Message message);

    @Query("SELECT * FROM messages WHERE (sender = :user1 AND receiver = :user2) OR (sender = :user2 AND receiver = :user1) ORDER BY timestamp")
    List<Message> getMessagesBetweenUsers(String user1, String user2);

    @Query("SELECT * FROM messages")
    List<Message> getAllMessages();
}