package com.example.testwork;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testwork.javabean.Cart;
import com.example.testwork.javabean.CartAdapter;
import com.example.testwork.javabean.Product;

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

        //Cart -- 列表视图  拿到这个元素后，用java代码将商品列表的种种元素添加到这个ListView上！！！
        //这样就能动态增加了，不会直接在xml上写死，也不会写一大堆重复代码！！！
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


        //----------------------数据库写入？？？---------------------------
        // 拿到Cart临时数据表，动态更新？
//        // 获取当前视图所关联的上下文对象
//        Context context = view.getContext();

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



        // 添加示例商品到购物车
//        Product product1 = new Product("Product 1", 10.99);
//        Product product2 = new Product("Product 2", 19.99);
//        Product product3 = new Product("Product 3", 13.99);
//        Product product4 = new Product("Product 4", 14.99);
//        //商品加入购物车
//        cart.addProduct(product1);
//        cart.addProduct(product2);
//        cart.addProduct(product3);
//        cart.addProduct(product4);



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