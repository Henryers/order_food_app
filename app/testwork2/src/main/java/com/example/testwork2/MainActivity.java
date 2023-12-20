package com.example.testwork2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.testwork2.javabean.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btnGoToShoppingCart;
    private ListView productList;
    private ProductAdapter adapter;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // 初始化商品列表数据
        initData();
    }

    private void initView() {
        btnGoToShoppingCart = findViewById(R.id.btn_go_to_shopping_cart);
        productList = findViewById(R.id.product_list);

        adapter = new ProductAdapter(this, products);
        productList.setAdapter(adapter);

        btnGoToShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                intent.putParcelableArrayListExtra("products", products);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        products.add(new Product("商品1", 10));
        products.add(new Product("商品2", 20));
        products.add(new Product("商品3", 30));
        products.add(new Product("商品4", 40));
        products.add(new Product("商品5", 50));
    }
}