package com.example.mechat.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.mechat.R;
import com.example.mechat.adapter.FriendListAdapter;
import com.example.mechat.database.AppDatabase;
import com.example.mechat.model.Friend;
import com.example.mechat.model.User;

import java.util.List;

public class FriendListActivity extends AppCompatActivity {
    private String currentUser;
    private ImageView ivUserAvatar;
    private TextView tvUsername;
    private ListView lvFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        currentUser = getIntent().getStringExtra("username");
        initViews();
        loadUserInfo();
        loadFriends();

        findViewById(R.id.btnViewDatabase).setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.mechat.activity.DatabaseViewActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        ivUserAvatar = findViewById(R.id.ivUserAvatar);
        tvUsername = findViewById(R.id.tvUsername);
        lvFriends = findViewById(R.id.lvFriends);
    }

    private void loadUserInfo() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            User user = db.userDao().getUser(currentUser);

            runOnUiThread(() -> {
                if (user != null && user.getAvatar() != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(user.getAvatar(), 0, user.getAvatar().length);
                    ivUserAvatar.setImageBitmap(bitmap);
                    tvUsername.setText(user.getUsername());
                }
            });
        }).start();
    }

    private void loadFriends() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<Friend> friends = db.friendDao().getFriendsByUser(currentUser);

            runOnUiThread(() -> {
                FriendListAdapter adapter = new FriendListAdapter(this, friends);
                lvFriends.setAdapter(adapter);

                lvFriends.setOnItemClickListener((parent, view, position, id) -> {
                    Friend friend = (Friend) parent.getItemAtPosition(position);
                    Intent intent = new Intent(FriendListActivity.this, ChatActivity.class);
                    intent.putExtra("currentUser", currentUser);
                    intent.putExtra("friendName", friend.getFriendName());
                    startActivity(intent);
                });
            });
        }).start();
    }
}