package com.example.finalwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private zMySqlHelper mYsqliteopenhelper;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //元素获取
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        TextView login = findViewById(R.id.login_login);
        TextView sign = findViewById(R.id.login_sign);
        TextView exit = findViewById(R.id.exit_app);
        //初始化数据库的对象
        mYsqliteopenhelper = new zMySqlHelper(this);
        //存一下当前账号，之后的“我的”页面会用作显示
        //由于每次都只是显示当前账号信息，因此使用SharedPreferences既能覆盖存储，又能保存数据，正好切合这一特性
        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        //点击登录按钮后，回调登录函数
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //点击注册按钮后，回调，跳转到注册页面
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign();
            }
        });

        //监听 - 退出程序
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                builder.setTitle("尊敬的用户");
                builder.setMessage("您确定要退出程序吗？")
                        .setPositiveButton("残忍离开", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 如果用户点击确定按钮，则所有数据销毁，并退出程序
                                finishAffinity();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 如果用户点击取消按钮，则关闭对话框并不做任何操作
                                dialog.cancel();
                            }
                        });
                //把该有的文字和设置都准备好，再在屏幕上进行展示
                builder.create().show();
            }
        });
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean login = mYsqliteopenhelper.login(username,password);

        //如果登录函数是返回true的，说明账号密码正确，能登录成功
        if (login) {
            // 保存账号和密码到SharedPreferences(等下登录后显示的账号username，就是此处最新存储的数据！！！)
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", username);
            editor.apply();    // 提交保存
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            // 创建意图，跳转到MainActivity
            Intent to_p1 = new Intent(login.this, MainActivity.class);
            to_p1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p1);
        }else if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void sign() {
        //意图跳转: 点击去注册
        Intent to_sign = new Intent(login.this, sign.class);
        to_sign.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(to_sign);
    }
}