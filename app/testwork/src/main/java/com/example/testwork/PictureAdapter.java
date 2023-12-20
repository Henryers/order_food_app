package com.example.testwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testwork.javabean.Product;

import java.util.List;
import java.util.Map;

public class PictureAdapter extends BaseAdapter {

    private Context mContext;
    private List<Map<String, Object>> mList;
    private zMysqlFoodHelper myCartSql;

    public PictureAdapter(Context context, List<Map<String, Object>> list, zMysqlFoodHelper cartSql) {
        mContext = context;
        mList = list;
        myCartSql = cartSql;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_apicture_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgView = convertView.findViewById(R.id.iv_img);
            viewHolder.titleView = convertView.findViewById(R.id.tv_foodname);
            viewHolder.contentView = convertView.findViewById(R.id.tv_foodprice);
            viewHolder.addToCartView = convertView.findViewById(R.id.addToCart);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map<String, Object> map = mList.get(position);
        int imgResId = (int) map.get("img");
        String title = (String) map.get("name");
        String content = (String) map.get("price");

        viewHolder.imgView.setImageResource(imgResId);
        viewHolder.titleView.setText(title);
        viewHolder.contentView.setText(content);

        viewHolder.addToCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodname = (String) map.get("name");
                String price = (String) map.get("price");
                int intprice = Integer.parseInt(price);
                int intquantity = 1;

                Toast.makeText(mContext, "你点击了" + position + ": " + foodname + ", " + price, Toast.LENGTH_SHORT).show();

                Product p = new Product(foodname, intprice, intquantity);
                myCartSql.add(p);

                // 更新数据或界面等操作
                // ...
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgView;
        TextView titleView;
        TextView contentView;
        TextView addToCartView;
    }
}

