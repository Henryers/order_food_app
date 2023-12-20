package com.example.testwork;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testwork.javabean.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnLogin;
    private MYsqliteopenhelper mYsqliteopenhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);
        //初始化数据库的对象
        mYsqliteopenhelper = new MYsqliteopenhelper(this);


        //点击注册按钮后，回调，跳转到注册页面
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        //点击登录按钮后，回调登录函数
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    //注册函数
    private void registerUser() {
        //跳到注册页面去注册
        Intent to_register = new Intent(MainActivity.this,Register.class);
        startActivity(to_register);
    }

/*    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        String savedUsername = sharedPref.getString("username", "");
        String savedPassword = sharedPref.getString("password", "");

        if (username.equals(savedUsername) && password.equals(savedPassword)) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }*/

    //登录函数
    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean login = mYsqliteopenhelper.login(username,password);

        //如果登录函数是返回true的，说明账号密码正确，能登录成功
        if (login) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            // 创建意图，跳转到 LoggedInActivity
            Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
            // 将账号信息打包，传入下一个页面
            intent.putExtra("username", username);
            startActivity(intent);
        }else if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

}
