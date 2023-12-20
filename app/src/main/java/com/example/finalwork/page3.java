package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class page3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        //获取底部导航栏的其他三个页面接口
        TextView p1 = findViewById(R.id.below_1);
        TextView p2 = findViewById(R.id.below_2);
        TextView p3_p2 = findViewById(R.id.p3_to_p2);
        TextView p4 = findViewById(R.id.below_4);

        //监听 - 跳转
        p1.setOnClickListener(view -> {
            Intent to_p1 = new Intent(page3.this, MainActivity.class);
            to_p1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p1);
        });
        p2.setOnClickListener(view -> {
            Intent to_p2 = new Intent(page3.this, page2.class);
            to_p2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p2);
        });
        //点击“立即点餐”跳转
        p3_p2.setOnClickListener(view -> {
            Intent p3_2 = new Intent(page3.this, page2.class);
            p3_2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(p3_2);
        });
        p4.setOnClickListener(view -> {
            Intent to_p4 = new Intent(page3.this, page4.class);
            to_p4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p4);
        });
    }
}