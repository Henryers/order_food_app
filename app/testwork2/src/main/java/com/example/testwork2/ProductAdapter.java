package com.example.testwork2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.testwork2.javabean.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> products;

    public ProductAdapter(MainActivity context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, null);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.tv_product_name);
            holder.productPrice = convertView.findViewById(R.id.tv_product_price);
            holder.productSelect = convertView.findViewById(R.id.cb_product_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("价格：" + product.getPrice());
        holder.productSelect.setChecked(product.isSelected());

        holder.productSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                product.setSelected(isChecked);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView productName;
        TextView productPrice;
        CheckBox productSelect;
    }
}