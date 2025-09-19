package com.example.mechat.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mechat.R;
import com.example.mechat.database.AppDatabase;
import com.example.mechat.util.ValidationUtil;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
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
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnChangePassword = findViewById(R.id.btnChangePassword);

        btnLogin.setOnClickListener(v -> attemptLogin());
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

    }

    private void attemptLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!ValidationUtil.isInputValid(username, password)) {
            Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            int isValid = db.userDao().validateUser(username, password);

            runOnUiThread(() -> {
                if (isValid > 0) {
                    Intent intent = new Intent(LoginActivity.this, FriendListActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}