package com.example.mechat.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mechat.R;
import com.example.mechat.database.AppDatabase;
import com.example.mechat.model.User;
import com.example.mechat.util.ValidationUtil;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etConfirmPassword;
    private RadioGroup rgAvatar;
    private byte[] selectedAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rgAvatar = findViewById(R.id.rgAvatar);

        // 预设头像数据
        int[] avatarResources = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3,
                R.drawable.avatar4, R.drawable.avatar5};
        RadioButton[] avatarButtons = {
                findViewById(R.id.rbAvatar1),
                findViewById(R.id.rbAvatar2),
                findViewById(R.id.rbAvatar3),
                findViewById(R.id.rbAvatar4),
                findViewById(R.id.rbAvatar5)
        };
        for (int i = 0; i < avatarButtons.length; i++) {
            avatarButtons[i].setBackgroundResource(avatarResources[i]);
        }

        rgAvatar.setOnCheckedChangeListener((group, checkedId) -> {
            int index = group.indexOfChild(findViewById(checkedId));
            if (index >= 0 && index < avatarResources.length) {
                // 将选中的头像转换为字节数组
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), avatarResources[index]);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                selectedAvatar = stream.toByteArray();
            }
        });

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> attemptRegister());
    }

    private void attemptRegister() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (!ValidationUtil.isInputValid(username, password, confirmPassword)) {
            Toast.makeText(this, "请填写所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!ValidationUtil.arePasswordsMatching(password, confirmPassword)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedAvatar == null) {
            Toast.makeText(this, "请选择头像", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            User existingUser = db.userDao().getUser(username);

            runOnUiThread(() -> {
                if (existingUser != null) {
                    Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
                } else {
                    User newUser = new User(username, password, selectedAvatar);
                    db.userDao().insert(newUser);

                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }).start();
    }
}