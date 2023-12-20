package com.example.finalwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class page4_logout extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private zMySqlHelper mySqlHelper;
    private String now_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4_logout);

        //获取两个输入框，一个注册，一个数据库对象
        etUsername = findViewById(R.id.logout_username);
        etPassword = findViewById(R.id.logout_password);
        TextView exit = findViewById(R.id.logout);
        mySqlHelper = new zMySqlHelper(this);
        //获取当前登录的账号username
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        now_username = preferences.getString("username", "");

        //监听 - 退出程序
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(page4_logout.this);
                builder.setTitle("尊敬的用户");
                builder.setMessage("您确定要进行注销吗？(此操作将清空所有数据且不可还原，请谨慎选择！)")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 如果用户点击确定按钮，则先进行判断
                                // 如果合法就删除数据，最后跳转到登录界面
                                // 如果不合法就提示密码错误（别再判断空了，套娃太多了...）

                                //先将传入的输入框信息转为字符串形式
                                String username = etUsername.getText().toString().trim();
                                String password = etPassword.getText().toString().trim();
                                //再判断输入框中的账号是否为当前登录的账号，因为要保证别人的账号不被强行注销
                                if(!username.equals(now_username)){
                                    Context context = view.getContext();
                                    Toast.makeText(context,"账号不合法，请重新输入",Toast.LENGTH_SHORT).show();
                                //输入的账号为当前登录的账号，合法，接下来对密码进行合法性判断
                                }else{
                                    //再调用操作数据库.java中的方法，进行注销
                                    boolean result = mySqlHelper.logout(username,password);
                                    if(result){
                                        Intent to_login = new Intent(page4_logout.this, login.class);
                                        // 获取当前视图所关联的上下文对象
                                        Context context = view.getContext();
                                        Toast.makeText(context,"注销成功",Toast.LENGTH_SHORT).show();
                                        to_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(to_login);
                                    }else{
                                        // 获取当前视图所关联的上下文对象
                                        Context context = view.getContext();
                                        Toast.makeText(context,"账号或密码错误",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
}