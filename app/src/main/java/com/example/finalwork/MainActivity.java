package com.example.finalwork;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HorizontalScrollView horizontalScrollView = findViewById(R.id.scroll);
        horizontalScrollView.post(() -> horizontalScrollView.scrollTo(0, 0));


        //获取视频
        VideoView tv1 = findViewById(R.id.video1);
        //拿到路径并播放
        String videoPath1 = "android.resource://" + getPackageName() + "/" + R.raw.food1;
        tv1.setVideoURI(Uri.parse(videoPath1));
        //tv1.start();        //先别急着播放，而是通过设置下面代码，将画面定格在第0毫秒
        tv1.setOnPreparedListener(mp -> {
            mp.seekTo(0); // 将视频跳转到第0毫秒
        });
        //添加别的视频辅助控件
        MediaController mediaController1 = new MediaController(this);
        mediaController1.setAnchorView(tv1);
        tv1.setMediaController(mediaController1);

        VideoView tv2 = findViewById(R.id.video2);
        String videoPath2 = "android.resource://" + getPackageName() + "/" + R.raw.food2;
        tv2.setVideoURI(Uri.parse(videoPath2));
        tv2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.seekTo(0); // 将视频跳转到第0毫秒
            }
        });
        //添加别的视频辅助控件
        MediaController mediaController2 = new MediaController(this);
        mediaController2.setAnchorView(tv2);
        tv2.setMediaController(mediaController2);

        VideoView tv3 = findViewById(R.id.video3);
        String videoPath3 = "android.resource://" + getPackageName() + "/" + R.raw.food3;
        tv3.setVideoURI(Uri.parse(videoPath3));
        tv3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.seekTo(0); // 将视频跳转到第0毫秒
            }
        });
        //添加别的视频辅助控件
        MediaController mediaController3 = new MediaController(this);
        mediaController3.setAnchorView(tv3);
        tv3.setMediaController(mediaController3);

        //推荐图片
        ImageView png1 = findViewById(R.id.p1_png1_1);
        png1.setImageResource(R.drawable.main101);
        ImageView png2 = findViewById(R.id.p1_png1_2);
        png2.setImageResource(R.drawable.main102);
        ImageView png3 = findViewById(R.id.p1_png2_1);
        png3.setImageResource(R.drawable.main201);
        ImageView png4 = findViewById(R.id.p1_png2_2);
        png4.setImageResource(R.drawable.main202);
        ImageView png5 = findViewById(R.id.p1_png3_1);
        png5.setImageResource(R.drawable.main301);
        ImageView png6 = findViewById(R.id.p1_png3_2);
        png6.setImageResource(R.drawable.main302);
        ImageView png7 = findViewById(R.id.p1_png4_1);
        png7.setImageResource(R.drawable.main401);
        ImageView png8 = findViewById(R.id.p1_png4_2);
        png8.setImageResource(R.drawable.main402);
        //对应文字
        TextView t1_1 = findViewById(R.id.p1_tx1_1);
        TextView t1_2 = findViewById(R.id.p1_tx1_2);
        TextView t2_1 = findViewById(R.id.p1_tx2_1);
        TextView t2_2 = findViewById(R.id.p1_tx2_2);
        TextView t3_1 = findViewById(R.id.p1_tx3_1);
        TextView t3_2 = findViewById(R.id.p1_tx3_2);
        TextView t4_1 = findViewById(R.id.p1_tx4_1);
        TextView t4_2 = findViewById(R.id.p1_tx4_2);
        //获取底部导航栏的其他三个页面接口
        TextView p2 = findViewById(R.id.below_2);
        TextView p3 = findViewById(R.id.below_3);
        TextView p4 = findViewById(R.id.below_4);

        //监听 - 店铺跳转
        png1.setOnClickListener(view -> {
            Intent to_noodles = new Intent(MainActivity.this, page2_noodles.class);
            to_noodles.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_noodles);
        });
        png2.setOnClickListener(view -> {
            Intent to_bigstall = new Intent(MainActivity.this, page2_bigstall.class);
            to_bigstall.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_bigstall);
        });
        png3.setOnClickListener(view -> {
            Intent to_chicken = new Intent(MainActivity.this, page2_chicken.class);
            to_chicken.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_chicken);
        });
        png4.setOnClickListener(view -> {
            Intent to_midfood = new Intent(MainActivity.this, page2_midfood.class);
            to_midfood.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_midfood);
        });
        png5.setOnClickListener(view -> {
            Intent to_F33 = new Intent(MainActivity.this, page2_Italian.class);
            to_F33.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_F33);
        });
        png6.setOnClickListener(view -> {
            Intent to_juicetea = new Intent(MainActivity.this, page2_juicetea.class);
            to_juicetea.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_juicetea);
        });
        png7.setOnClickListener(view -> {
            Intent to_pizza = new Intent(MainActivity.this, page2_pizza.class);
            to_pizza.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_pizza);
        });
        png8.setOnClickListener(view -> {
            Intent to_northwest = new Intent(MainActivity.this, page2_northwest.class);
            to_northwest.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_northwest);
        });

        t1_1.setOnClickListener(view -> {
            Intent to_noodles = new Intent(MainActivity.this, page2_noodles.class);
            to_noodles.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_noodles);
        });
        t1_2.setOnClickListener(view -> {
            Intent to_bigstall = new Intent(MainActivity.this, page2_bigstall.class);
            to_bigstall.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_bigstall);
        });
        t2_1.setOnClickListener(view -> {
            Intent to_chicken = new Intent(MainActivity.this, page2_chicken.class);
            to_chicken.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_chicken);
        });
        t2_2.setOnClickListener(view -> {
            Intent to_midfood = new Intent(MainActivity.this, page2_midfood.class);
            to_midfood.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_midfood);
        });
        t3_1.setOnClickListener(view -> {
            Intent to_F33 = new Intent(MainActivity.this, page2_Italian.class);
            to_F33.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_F33);
        });
        t3_2.setOnClickListener(view -> {
            Intent to_juicetea = new Intent(MainActivity.this, page2_juicetea.class);
            to_juicetea.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_juicetea);
        });
        t4_1.setOnClickListener(view -> {
            Intent to_pizza = new Intent(MainActivity.this, page2_pizza.class);
            to_pizza.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_pizza);
        });
        t4_2.setOnClickListener(view -> {
            Intent to_northwest = new Intent(MainActivity.this, page2_northwest.class);
            to_northwest.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_northwest);
        });

        //监听 - 导航栏跳转
        p2.setOnClickListener(view -> {
            Intent to_p2 = new Intent(MainActivity.this, page2.class);
            to_p2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p2);
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击跳转到购物车，需判断购物车是否为空，来决定跳转到哪一个xml页面
                sqLiteDatabase = openOrCreateDatabase("MYsqlite.db", MODE_PRIVATE, null);
//                // 判断表是否存在
//                boolean isTableExists = isTableExists("cart", sqLiteDatabase);
//
//                // 如果表不存在，创建表
//                if (!isTableExists) {
//                    // 执行创建表的操作
//                    // ...
//                }
                sqLiteDatabase.execSQL("create table if not exists cart(foodname varchar(32),foodprice varchar(32),quantity varchar(32))");
                Log.e("1","先执行这里的建表cart，否则下面的query一直报错没有建表");
                Cursor cursor = sqLiteDatabase.query("cart", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String foodname = null;
                        String foodprice = null;
                        int quantity = 0;
                        try {
                            //拿到下标，游标指向相应下标位置即可拿到对应数据信息
                            foodname = cursor.getString(cursor.getColumnIndexOrThrow("foodname"));
                            foodprice = cursor.getString(cursor.getColumnIndexOrThrow("foodprice"));
                            quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                        } catch (IllegalArgumentException e) {
                            Log.e(TAG, "getColumnIndexOrThrow error: " + e.getMessage());
                        }
                        if (quantity !=0) {
                            //但凡找到一个数量不为0的商品都说明购物车不为空，需跳到 page3_Cart
                            Intent to_p3cart = new Intent(MainActivity.this, page3_Cart.class);
                            to_p3cart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(to_p3cart);
                            return;
                        }
                    } while (cursor.moveToNext());
                }
                // 关闭游标和数据库连接
                cursor.close();
                sqLiteDatabase.close();
                //前面的quantity都为0,没有return,所以要执行以下代码，跳转到 page3
                Intent to_p3 = new Intent(MainActivity.this, page3.class);
                to_p3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_p3);
            }
        });
        p4.setOnClickListener(view -> {
            Intent to_p4 = new Intent(MainActivity.this, page4.class);
            to_p4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            to_p4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p4);
        });
    }
}