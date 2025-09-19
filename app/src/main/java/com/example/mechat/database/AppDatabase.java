package com.example.mechat.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mechat.database.FriendDao;
import com.example.mechat.database.MessageDao;
import com.example.mechat.model.User;
import com.example.mechat.model.Friend;
import com.example.mechat.model.Message;
import com.example.mechat.R;


// 其他导入...
import java.io.ByteArrayOutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Friend.class, Message.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract FriendDao friendDao();
    public abstract MessageDao messageDao();

    private static volatile AppDatabase instance;
    private Context appContext;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "mechat-db")
                    .fallbackToDestructiveMigration()
                    .build();

            instance.appContext = context.getApplicationContext();

            // 预置数据
            instance.populateInitialData();
        }
        return instance;
    }

    private void populateInitialData() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // 预置5个好友
            if (friendDao().getAllFriends().isEmpty()) {
                // 添加预设好友数据
                addPresetFriends();
            }
        });
    }

    private void addPresetFriends() {
        // 预设好友名称
        String[] friendNames = {"张三", "李四", "王五", "赵六", "钱七"};

        // 预设头像资源ID
        int[] avatarResources = {
                R.drawable.avatar1,
                R.drawable.avatar2,
                R.drawable.avatar3,
                R.drawable.avatar4,
                R.drawable.avatar5
        };

        // 确保资源存在
        for (int i = 0; i < friendNames.length; i++) {
            // 将图片资源转换为字节数组
            byte[] avatarData = convertDrawableToByteArray(avatarResources[i]);

            if (avatarData != null) {
                // 创建好友对象并插入数据库
                Friend friend = new Friend("default", friendNames[i], avatarData);
                friendDao().insert(friend);
            }
        }
    }

    private byte[] convertDrawableToByteArray(int drawableId) {
        try {
            // 将Drawable资源转换为Bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), drawableId);

            if (bitmap != null) {
                // 将Bitmap转换为字节数组
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                return stream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}