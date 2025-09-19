package com.example.mechat.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mechat.R;
import com.example.mechat.adapter.ChatAdapter; // 确保正确导入
import com.example.mechat.database.AppDatabase;
import com.example.mechat.model.Message;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private String currentUser;
    private String friendName;
    private ListView lvMessages;
    private EditText etMessage;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        currentUser = getIntent().getStringExtra("currentUser");
        friendName = getIntent().getStringExtra("friendName");

        initViews();
        loadMessages();

        findViewById(R.id.btnSend).setOnClickListener(v -> sendMessage());
        findViewById(R.id.ivFriendAvatar).setOnClickListener(v -> openFriendDetail());
    }

    private void initViews() {
        TextView tvFriendName = findViewById(R.id.tvFriendName);
        tvFriendName.setText(friendName);

        lvMessages = findViewById(R.id.lvMessages);
        etMessage = findViewById(R.id.etMessage);
    }

    private void loadMessages() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<Message> messages = db.messageDao().getMessagesBetweenUsers(currentUser, friendName);

            runOnUiThread(() -> {
                adapter = new ChatAdapter(this, messages, currentUser);
                lvMessages.setAdapter(adapter);
                scrollToListBottom();
            });
        }).start();
    }

    private void sendMessage() {
        String content = etMessage.getText().toString().trim();
        if (content.isEmpty()) return;

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            Message message = new Message(currentUser, friendName, content, System.currentTimeMillis());
            db.messageDao().insert(message);

            runOnUiThread(() -> {
                etMessage.setText("");
                adapter.addMessage(message);
                scrollToListBottom();
            });
        }).start();
    }

    private void scrollToListBottom() {
        lvMessages.post(() -> lvMessages.setSelection(adapter.getCount() - 1));
    }

    private void openFriendDetail() {
        Intent intent = new Intent(this, FriendDetailActivity.class);
        intent.putExtra("friendName", friendName);
        startActivity(intent);
    }
}