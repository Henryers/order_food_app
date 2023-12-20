package com.example.finalwork;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalwork.javabean.Cart;
import com.example.finalwork.javabean.CartAdapter;
import com.example.finalwork.javabean.Product;

import java.util.Map;

public class page3_Cart extends AppCompatActivity {
    private Cart cart;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3_cart);

        //返回上一页的具体店铺
        TextView back = findViewById(R.id.cart_back);
        //获取底部导航栏的其他三个页面接口
        TextView p1 = findViewById(R.id.below_1);
        TextView p2 = findViewById(R.id.below_2);
        TextView p4 = findViewById(R.id.below_4);

        //监听 - 跳转
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //根据打包过来的信息，决定跳转回哪个界面
                Bundle bundle = getIntent().getExtras();
                //根据键key拿到值
                String receive = bundle.getString("name");
                if(receive.equals("杨氏炸鸡")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_chicken.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }else if(receive.equals("东北面馆")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_noodles.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }else if(receive.equals("F33意式餐厅")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_Italian.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }else if(receive.equals("福家大排档")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_bigstall.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }else if(receive.equals("凌家中餐")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_midfood.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }else if(receive.equals("披萨小屋")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_pizza.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }else if(receive.equals("何牌西北菜")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_northwest.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }else if(receive.equals("故式果茶")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_juicetea.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }else if(receive.equals("左町日料")){
                    Intent to_sp = new Intent(page3_Cart.this, page2_japanfood.class);
                    to_sp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(to_sp);
                }
            }
        });
        p1.setOnClickListener(view -> {
            Intent to_p1 = new Intent(page3_Cart.this, MainActivity.class);
            to_p1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p1);
        });
        p2.setOnClickListener(view -> {
            Intent to_p2 = new Intent(page3_Cart.this, page2.class);
            to_p2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p2);
        });
        p4.setOnClickListener(view -> {
            Intent to_p4 = new Intent(page3_Cart.this, page4.class);
            to_p4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_p4);
        });

        // 在 CartActivity 的 onCreate 方法中接收传递的商品数据
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            Product product = (Product) intent.getSerializableExtra("product");
            cart.addProduct(product); // 将商品添加到购物车
        }

        //Cart -- 列表视图  拿到这个元素后，用java代码将商品列表的种种元素添加到这个ListView上
        //这样就能动态增加了，不会直接在xml上写死，也不会写一大堆重复代码
        ListView listView = findViewById(R.id.list_view);
        //按钮
        Button checkoutButton = findViewById(R.id.checkout_button);

        //新建一个购物车，其实就是一个商品列表（Products）
        cart = new Cart();
        //创建出一个购物车列表项适配器，有参构造函数就已经将所有数据处理好了
        //this上下文对象， cart.getProducts()就是拿到商品列表products
        CartAdapter cartAdapter = new CartAdapter(this, cart.getProducts());
        //数据已处理好，直接写入xml的Listview中
        listView.setAdapter(cartAdapter);


        // 拿到数据库中的 Cart临时数据表，动态更新，每次跳转到这个页面就会进行初始化，将相关商品信息写入并进行相应展示
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
                if (foodname != null && foodprice != null && quantity !=0) {
                    Product product = new Product(foodname,Double.parseDouble(foodprice),quantity);
                    cart.addProduct(product);
                }
            } while (cursor.moveToNext());
        }
        // 关闭游标和数据库连接
        cursor.close();
        sqLiteDatabase.close();


        //打开这个页面时先显示当前总价
        TextView shownum = findViewById(R.id.shownum);
        double totalprice = cart.getTotalPrice();
        shownum.setText(String.valueOf(totalprice));
        //点击结算按钮，进行支付
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPrice = cart.getTotalPrice();
                AlertDialog.Builder builder = new AlertDialog.Builder(page3_Cart.this);
                builder.setTitle("总价为"+totalPrice+"元");
                builder.setMessage("您确定要进行支付吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // 如果用户点击确定按钮，则关闭对话框并显示支付成功
                            Toast.makeText(page3_Cart.this, "支付成功！", Toast.LENGTH_LONG).show();
                            //关闭对话框之前先将数据库的所有quantity都变为0
                            sqLiteDatabase = openOrCreateDatabase("MYsqlite.db", MODE_PRIVATE, null);
                            Cursor cursor = sqLiteDatabase.query("cart", null, null, null, null, null, null);
                            if (cursor.moveToFirst()) {
                                do {
                                    try {
                                        //拿到下标，游标指向相应下标位置即可拿到对应数据信息
                                        sqLiteDatabase.delete("cart",null, null);
                                    } catch (IllegalArgumentException e) {
                                        Log.e(TAG, "getColumnIndexOrThrow error: " + e.getMessage());
                                    }
                                } while (cursor.moveToNext());
                            }
                            // 关闭游标和数据库连接
                            cursor.close();
                            sqLiteDatabase.close();
                            //可以关闭对话框了
                            dialog.cancel();
                            //跳转到page3那个空空如也的界面-
                            Intent to_p3 = new Intent(page3_Cart.this, page3.class);
                            to_p3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(to_p3);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // 如果用户点击取消按钮，则关闭对话框，不做任何操作
                            dialog.cancel();
                        }
                    });
                //把该有的文字和设置都准备好，再在屏幕上进行展示
                builder.create().show();
            }
        });


        //为整个列表设置点击事件，无法精确到+-这两个符号？ 那就使用数据库来操作
        //无论点列表的哪个位置，我都能利用数据库的实时更新，将更改后的数据写在这个页面的总价上(除了+-都能点)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                double total = 0;
                sqLiteDatabase = openOrCreateDatabase("MYsqlite.db", MODE_PRIVATE, null);
                Cursor cursor = sqLiteDatabase.query("cart", null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        try {
                            //拿到下标，游标指向相应下标位置即可拿到对应数据信息
                            double price = cursor.getInt(cursor.getColumnIndexOrThrow("foodprice"));
                            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                            //总价追加计算
                            total += quantity*price;
                        } catch (IllegalArgumentException e) {
                            Log.e(TAG, "getColumnIndexOrThrow error: " + e.getMessage());
                        }

                    } while (cursor.moveToNext());
                }
                shownum.setText(String.valueOf(total));
            }
        });
    }
}