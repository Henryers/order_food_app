package com.example.testwork2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.testwork2.javabean.Product;

import java.util.ArrayList;

public class ShoppingCartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> shoppingCartProducts;

    public ShoppingCartAdapter(Context context, ArrayList<Product> shoppingCartProducts) {
        this.context = context;
        this.shoppingCartProducts = shoppingCartProducts;
    }

    @Override
    public int getCount() {
        return shoppingCartProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCartProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart_product, null);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.tv_sc_product_name);
            holder.productPrice = convertView.findViewById(R.id.tv_sc_product_price);
            holder.productNum = convertView.findViewById(R.id.tv_sc_product_num);
            holder.btnAdd = convertView.findViewById(R.id.btn_sc_add);
            holder.btnMinus = convertView.findViewById(R.id.btn_sc_minus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = shoppingCartProducts.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("价格：" + product.getPrice());
        holder.productNum.setText("数量：" + product.getNum());

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setNum(product.getNum() + 1);
                notifyDataSetChanged();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getNum() > 0) {
                    product.setNum(product.getNum() - 1);
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productNum;
        Button btnAdd;
        Button btnMinus;
    }
}