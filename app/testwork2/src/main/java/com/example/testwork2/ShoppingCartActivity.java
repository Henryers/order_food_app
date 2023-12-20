package com.example.testwork2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testwork2.javabean.Product;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {
    private ListView shoppingCartList;
    private TextView totalPrice;

    private ShoppingCartAdapter adapter;
    private ArrayList<Product> shoppingCartProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        initView();
        initData();
    }

    private void initView() {
        shoppingCartList = findViewById(R.id.shopping_cart_list);
        totalPrice = findViewById(R.id.total_price_tv);

        adapter = new ShoppingCartAdapter(this, shoppingCartProducts);
        shoppingCartList.setAdapter(adapter);
    }

    private void initData() {
        // 获取Intent中传递的数据
        Intent intent = getIntent();
        ArrayList<Product> products = intent.getParcelableArrayListExtra("products");

        // 将商品添加到购物车并更新UI
        for (Product product : products) {
            if (product.isSelected()) {
                shoppingCartProducts.add(product);
            }
        }

        adapter.notifyDataSetChanged();

        // 计算并显示总价
        double total = 0;
        for (Product product : shoppingCartProducts) {
            total += product.getPrice();
        }
        totalPrice.setText("总价：" + total);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clear_cart) {
            shoppingCartProducts.clear();
            adapter.notifyDataSetChanged();
            totalPrice.setText("总价：0");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}