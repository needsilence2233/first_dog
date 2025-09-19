package com.example.mechat.activity;


import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mechat.R;
import com.example.mechat.database.AppDatabase;
import com.example.mechat.model.Friend;

public class FriendDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        String friendName = getIntent().getStringExtra("friendName");
        loadFriendDetails(friendName);
    }

    private void loadFriendDetails(String friendName) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            Friend friend = db.friendDao().getFriendByName(friendName);

            runOnUiThread(() -> {
                if (friend != null) {
                    ImageView ivAvatar = findViewById(R.id.ivAvatar);
                    TextView tvName = findViewById(R.id.tvName);

                    if (friend.getFriendAvatar() != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(friend.getFriendAvatar(), 0, friend.getFriendAvatar().length);
                        ivAvatar.setImageBitmap(bitmap);
                    }
                    tvName.setText(friend.getFriendName());
                }
            });
        }).start();
    }
}