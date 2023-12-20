package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.net.Inet4Address;

public class page4_help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4_help);

        //点击返回
        TextView back = findViewById(R.id.help_back);
        back.setOnClickListener(view -> {
            Intent to_p4 = new Intent(page4_help.this, page4.class);
            to_p4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p4);
        });
    }
}