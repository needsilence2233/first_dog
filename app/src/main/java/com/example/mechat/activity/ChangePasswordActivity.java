package com.example.mechat.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mechat.database.AppDatabase;
import com.example.mechat.util.ValidationUtil;
import com.example.mechat.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText etUsername, etOldPassword, etNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etUsername = findViewById(R.id.etUsername);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);

        Button btnConfirm = findViewById(R.id.btnConfirm);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnConfirm.setOnClickListener(v -> attemptChangePassword());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void attemptChangePassword() {
        String username = etUsername.getText().toString().trim();
        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();

        if (!ValidationUtil.isInputValid(username, oldPassword, newPassword)) {
            Toast.makeText(this, "请填写所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            int result = db.userDao().updatePassword(username, oldPassword, newPassword);

            runOnUiThread(() -> {
                if (result > 0) {
                    Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "用户名或旧密码错误", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}