package com.example.mechat.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mechat.R;
import com.example.mechat.database.AppDatabase;
import com.example.mechat.model.User;
import com.example.mechat.model.Friend;
import com.example.mechat.model.Message;

import java.util.List;

public class DatabaseViewActivity extends AppCompatActivity {
    private DatabaseView databaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_view);

        databaseView = findViewById(R.id.databaseView);
        loadDatabaseData();
    }

    private void loadDatabaseData() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<User> users = db.userDao().getAllUsers();
            List<Friend> friends = db.friendDao().getAllFriends();
            List<Message> messages = db.messageDao().getAllMessages();

            runOnUiThread(() -> {
                databaseView.setData(users, friends, messages);
                databaseView.invalidate();
            });
        }).start();
    }

    public static class DatabaseView extends View {
        private List<User> users;
        private List<Friend> friends;
        private List<Message> messages;
        private Paint paint;

        public DatabaseView(android.content.Context context) {
            super(context);
            init();
        }

        public DatabaseView(android.content.Context context, android.util.AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(24);
        }

        public void setData(List<User> users, List<Friend> friends, List<Message> messages) {
            this.users = users;
            this.friends = friends;
            this.messages = messages;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (users == null || friends == null || messages == null) return;

            int y = 50;
            canvas.drawText("用户表 (" + users.size() + " 条记录)", 20, y, paint);
            y += 30;

            for (User user : users) {
                canvas.drawText("用户: " + user.getUsername(), 40, y, paint);
                y += 25;
            }

            y += 20;
            canvas.drawText("好友表 (" + friends.size() + " 条记录)", 20, y, paint);
            y += 30;

            for (Friend friend : friends) {
                canvas.drawText("好友: " + friend.getFriendName() + " (属于: " + friend.getUsername() + ")", 40, y, paint);
                y += 25;
            }

            y += 20;
            canvas.drawText("消息表 (" + messages.size() + " 条记录)", 20, y, paint);
            y += 30;

            for (Message message : messages) {
                canvas.drawText("消息: " + message.getSender() + " -> " + message.getReceiver() + ": " +
                        message.getContent(), 40, y, paint);
                y += 25;
                if (y > getHeight() - 50) break;
            }
        }
    }
}