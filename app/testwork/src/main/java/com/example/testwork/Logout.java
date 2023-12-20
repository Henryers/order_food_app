package com.example.testwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Logout extends AppCompatActivity {

    private MYsqliteopenhelper mYsqliteopenhelper;
    private EditText etUsername;
    private EditText etPassword;
    private TextView exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        //获取输入框的账号密码
        etUsername = findViewById(R.id.logout_username);
        etPassword = findViewById(R.id.logout_password);
        //注销按钮
        exit = findViewById(R.id.btn_logout);
        //初始化数据库的对象
        mYsqliteopenhelper = new MYsqliteopenhelper(this);

        //监听 - 退出程序
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Logout.this);
                builder.setTitle("尊敬的用户");
                builder.setMessage("您确定要进行注销吗？(此操作将清空所有数据且不可还原，请谨慎选择！)")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 如果用户点击确定按钮，则先进行判断
                                // 如果合法就删除数据，最后跳转到登录界面
                                // 如果不合法就提示密码错误（别再判断空了，套娃太多了！！！）

                                //先将传入的输入框信息转为字符串形式
                                String username = etUsername.getText().toString().trim();
                                String password = etPassword.getText().toString().trim();
                                //再调用操作数据库.java中的方法，进行注销
                                boolean result = mYsqliteopenhelper.logout(username,password);
                                if(result){
                                    Intent it1 = new Intent(Logout.this, MainActivity.class);
                                    it1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(it1);
                                }else{
                                    // 获取当前视图所关联的上下文对象
                                    Context context = view.getContext();
                                    Toast.makeText(context,"账号或密码错误",Toast.LENGTH_SHORT).show();
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