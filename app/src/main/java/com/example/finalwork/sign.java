package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalwork.javabean.User;

public class sign extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText confirm;
    private zMySqlHelper mySqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        //获取元素
        TextView back = findViewById(R.id.return_login);

        username = findViewById(R.id.sign_etUsername);
        password = findViewById(R.id.sign_etPassword);
        confirm = findViewById(R.id.sign_confirm_etPassword);

        TextView sign = findViewById(R.id.sign);
        //初始化数据库的对象
        mySqlHelper = new zMySqlHelper(this);

        //监听+跳转
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回键: 到login界面
                Intent it1 = new Intent(sign.this, login.class);
                it1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it1);
            }
        });

        //点击注册按钮后，回调，如果注册成功，则跳转到登录页面
        sign.setOnClickListener(new View.OnClickListener() {
            //注册键： 也是到login界面
            @Override
            public void onClick(View v) {
                sign_to_login();
            }
        });
    }

    private void sign_to_login() {
        //获取输入框的文本内容，去掉多余空格并转为字符串，最后赋给变量接收
        String Username = username.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String Confirm = confirm.getText().toString().trim();

        User u = new User(Username,Password);
        //如果获取的账号或者密码字符串为空，那么就是没输入完，在下方弹出提示框：请输入账号和密码（持续2秒）
        if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
        } else if (!Password.equals(Confirm)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        } else{
            //这个分支肯定合法，直接来一手注册！
            long l = mySqlHelper.register(u);
            if(l == 1){
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                //随后跳转回登录界面（MainActivity）界面，进行登录
                Intent to_login = new Intent(sign.this,login.class);
                to_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_login);
            }else {
                Toast.makeText(this, "账号已存在，请重新输入", Toast.LENGTH_SHORT).show();
            }
        }
    }
}