package com.example.finalwork;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class page2 extends AppCompatActivity {

    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        //获取具体店铺 - 跳转
        TextView chicken = findViewById(R.id.p2_chicken);
        TextView noodles = findViewById(R.id.p2_noodles);
        TextView Italian = findViewById(R.id.p2_Italian);
        TextView bigstall = findViewById(R.id.p2_bigstall);
        TextView midfood = findViewById(R.id.p2_midfood);
        TextView pizza = findViewById(R.id.p2_pizza);
        TextView northwest = findViewById(R.id.p2_northwest);
        TextView juicetea = findViewById(R.id.p2_juicetea);
        TextView japanfood = findViewById(R.id.p2_japanfood);
        //店铺图片,获取后展示
        ImageView png1 = findViewById(R.id.png1);
        png1.setImageResource(R.drawable.chicken);
        ImageView png2 = findViewById(R.id.png2);
        png2.setImageResource(R.drawable.noodles);
        ImageView png3 = findViewById(R.id.png3);
        png3.setImageResource(R.drawable.italian);
        ImageView png4 = findViewById(R.id.png4);
        png4.setImageResource(R.drawable.bigstall);
        ImageView png5 = findViewById(R.id.png5);
        png5.setImageResource(R.drawable.midfood);
        ImageView png6 = findViewById(R.id.png6);
        png6.setImageResource(R.drawable.pizza);
        ImageView png7 = findViewById(R.id.png7);
        png7.setImageResource(R.drawable.northwest);
        ImageView png8 = findViewById(R.id.png8);
        png8.setImageResource(R.drawable.juicetea);
        ImageView png9 = findViewById(R.id.png9);
        png9.setImageResource(R.drawable.japanfood);
        //获取底部导航栏的其他三个页面接口
        TextView p1 = findViewById(R.id.below_1);
        TextView p3 = findViewById(R.id.below_3);
        TextView p4 = findViewById(R.id.below_4);

        //监听 - 店铺跳转
        //  1
        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_chicken = new Intent(page2.this, page2_chicken.class);
                to_chicken.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_chicken);
            }
        });
        //  2
        noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_noodles = new Intent(page2.this, page2_noodles.class);
                to_noodles.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_noodles);
            }
        });
        //  3
        Italian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_Italian = new Intent(page2.this, page2_Italian.class);
                to_Italian.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_Italian);
            }
        });
        //  4
        bigstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_bigstall = new Intent(page2.this, page2_bigstall.class);
                to_bigstall.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_bigstall);
            }
        });
        //  5
        midfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_midfood = new Intent(page2.this, page2_midfood.class);
                to_midfood.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_midfood);
            }
        });
        //  6
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_pizza = new Intent(page2.this, page2_pizza.class);
                to_pizza.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_pizza);
            }
        });
        //  7
        northwest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_northwest = new Intent(page2.this, page2_northwest.class);
                to_northwest.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_northwest);
            }
        });
        //  8
        juicetea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_juicetea = new Intent(page2.this, page2_juicetea.class);
                to_juicetea.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_juicetea);
            }
        });
        //  9
        japanfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_japanfood = new Intent(page2.this, page2_japanfood.class);
                to_japanfood.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_japanfood);
            }
        });

        //监听 - 底部导航栏跳转
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_p1 = new Intent(page2.this, MainActivity.class);
                to_p1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_p1);
            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击跳转到购物车，需判断购物车是否为空，来决定跳转到哪一个xml页面
                sqLiteDatabase = openOrCreateDatabase("MYsqlite.db", MODE_PRIVATE, null);
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
                            Intent to_p3cart = new Intent(page2.this, page3_Cart.class);
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
                Intent to_p3 = new Intent(page2.this, page3.class);
                to_p3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_p3);
            }
        });
        p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_p4 = new Intent(page2.this, page4.class);
                to_p4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_p4);
            }
        });
    }
}