package com.example.testwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testwork.javabean.User;

public class Register extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;
    private MYsqliteopenhelper mYsqliteopenhelper_rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_username_rg);
        etPassword = findViewById(R.id.et_password_rg);
        btnRegister = findViewById(R.id.btn_register_rg);
        //初始化数据库的对象
        mYsqliteopenhelper_rg = new MYsqliteopenhelper(this);

        //点击注册按钮后，回调，如果注册成功，则跳转到登录页面
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser_rg();
            }
        });
    }

    private void registerUser_rg() {
        //获取输入框的文本内容，去掉多余空格并转为字符串，最后赋给变量接收
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        User u = new User(username,password);
        long l = mYsqliteopenhelper_rg.register(u);
        if(l != -1){
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            //随后跳转回登录界面（MainActivity）界面，进行登录
            Intent to_login = new Intent(Register.this,MainActivity.class);
            startActivity(to_login);
            return;
        }
        //l == -1 时，才执行以下代码，说明要么没输入，要么账号不合法，以下分别进行判断
        //如果获取的账号或者密码字符串为空，那么就是没输入完，在下方弹出提示框：请输入账号和密码（持续2秒）
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "密码只能包含数字", Toast.LENGTH_SHORT).show();
        }

/*        // 检查账号是否只包含英文字母，正则表达式匹配，如果匹配不成功，那么下方弹出提示框：账号只能包含英文字母（持续2秒）
        if (!username.matches("[a-zA-Z]+")) {
            Toast.makeText(this, "账号只能包含英文字母", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查密码是否只包含数字
        if (!password.matches("\\d+")) {
            Toast.makeText(this, "密码只能包含数字", Toast.LENGTH_SHORT).show();
            return;
        }

        //省流总结：Toast是下方小提示框
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();*/
    }
}