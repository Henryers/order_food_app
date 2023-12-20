package com.example.testwork;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoggedInActivity extends AppCompatActivity {

    private TextView tvUsername;
    private TextView logOut;
    private MYsqliteopenhelper mYsqliteopenhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        tvUsername = findViewById(R.id.tv_username);
        logOut = findViewById(R.id.btn_distroy);
        //初始化数据库的对象
        mYsqliteopenhelper = new MYsqliteopenhelper(this);

        // 获取传递的账号数据
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // 在界面上显示账号
        tvUsername.setText("欢迎，" + username + "！");

        // 直接一手注销！ -- 跳到注销页面进行注销
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_logout = new Intent(LoggedInActivity.this, Logout.class);
                startActivity(to_logout);
            }
        });
    }
}
