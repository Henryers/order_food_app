package com.example.finalwork;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalwork.javabean.Cart;
import com.example.finalwork.javabean.CartAdapter;
import com.example.finalwork.javabean.Product;

public class CartActivity extends AppCompatActivity {
    private ListView listView;
    private CartAdapter cartAdapter;
    private Cart cart;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // 在 CartActivity 的 onCreate 方法中接收传递的商品数据
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            Product product = (Product) intent.getSerializableExtra("product");
            cart.addProduct(product); // 将商品添加到购物车
        }

        //Cart -- 列表视图  拿到这个元素后，用java代码将商品列表的种种元素添加到这个ListView上
        //这样就能动态增加了，不会直接在xml上写死，也不会写一大堆重复代码
        listView = findViewById(R.id.list_view);
        //按钮
        Button checkoutButton = findViewById(R.id.checkout_button);

        //新建一个购物车，其实就是一个商品列表（Products）
        cart = new Cart();
        //创建出一个购物车列表项适配器，有参构造函数就已经将所有数据处理好了！
        //this上下文对象， cart.getProducts()就是拿到商品列表products
        cartAdapter = new CartAdapter(this, cart.getProducts());
        //数据已处理好，直接写入xml的Listview中
        listView.setAdapter(cartAdapter);


        //----------将当前数据库里的所有 products都 new出来，并写入购物车Cart中-----------
        //有Cart之后，上面的cart.getProducts()才会有上面内容，cartAdapter才有内容，因此listView才有内容，能在屏幕上显示一行行商品信息
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

        //点击结算按钮，计算总价
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPrice = cart.getTotalPrice();
                Toast.makeText(CartActivity.this, "Total Price: $" + totalPrice, Toast.LENGTH_SHORT).show();
            }
        });
    }
}