package com.example.ppt1_1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button confirm;
    private Button cancel;
    private EditText input;
    private ProgressBar progressBar;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        textView.setText("用户登录");

        progressBar = findViewById(R.id.progress_bar);//进度条绑定


        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {   //此处添加逻辑代码
                input = (EditText)findViewById(R.id.username);
                String username = input.getText().toString();
                input = (EditText)findViewById(R.id.password);
                String password = input.getText().toString();
                Toast.makeText(MainActivity.this, username, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, password, Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "confirm", Toast.LENGTH_SHORT).show();

                // 显示进度条
                progressBar.setVisibility(View.VISIBLE);

                // 使用Handler模拟进度更新
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    int progress = 0;
                    @Override
                    public void run() {
                        if (progress <= 100) {
                            progressBar.setProgress(progress);
                            progress++;
                            handler.postDelayed(this, 50); // 每50ms更新一次
                        }
                    }
                });

                //ProgressDialog progressdialog = new ProgressDialog(MainActivity.this);
                //progressdialog.setTitle("Loading...");
                //progressdialog.setCancelable(true);
                //progressdialog.show();
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
                alertdialog.setTitle("确认退出登录界面？");
                alertdialog.setMessage("确认？");
                alertdialog.setCancelable(false);
                alertdialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface alertdialog, int which) {}
                });
                alertdialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface alertdialog, int which) {}
                });
                alertdialog.show();
            }
        });

    }
}

