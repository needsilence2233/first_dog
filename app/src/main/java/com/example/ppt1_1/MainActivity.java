package com.example.ppt1_1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // 修正1：统一变量命名和初始化
    private Button confirm;
    private Button cancel;
    private EditText usernameEditText;  // 修正输入框引用
    private EditText passwordEditText;
    private ProgressBar progressBar;

    // 修正2：添加预设凭证（实际开发中应使用安全存储）
    private static final String EXPECTED_USERNAME = "admin";
    private static final String EXPECTED_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView); // 自动类型转换（无需(ImageView)）
        imageView.setAlpha(128); // 推荐使用中间值 128（50% 透明）


        // 修正3：正确初始化所有视图组件
        TextView textView = findViewById(R.id.textView);
        textView.setText("用户登录");

        progressBar = findViewById(R.id.progress_bar);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        // 修正4：优化确认按钮逻辑
        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // 修正5：添加空值校验
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            // 修正6：修复上下文引用问题
            if (EXPECTED_USERNAME.equals(username) && EXPECTED_PASSWORD.equals(password)) {
                animateProgressBar();
            } else {
                new AlertDialog.Builder(MainActivity.this)  // 修正上下文引用
                        .setTitle("输入错误")
                        .setMessage("您输入的内容不符合要求，请重新输入！")
                        .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        // 修正7：完善取消按钮逻辑
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("确认退出登录界面？")
                    .setMessage("确认？")
                    .setPositiveButton("确认", (dialog, which) -> finish())  // 关闭当前Activity
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    // 进度条动画方法（保持不变）
    private void animateProgressBar() {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        animator.setDuration(1000);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(MainActivity.this, "验证成功，进度已更新", Toast.LENGTH_SHORT).show();
            }
        });
    }
}