package com.example.finalwork.javabean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalwork.R;
import com.example.finalwork.zMysqlFoodHelper;

import java.util.List;
import java.util.Locale;

//重写列表适配器，将每行商品逐一写入ListView控件中
//将数据源（例如列表、数组，就是前面的Cart类！）与列表视图（如 ListView）进行适配，以便将数据显示在列表中。
//CartAdapter继承自 ArrayAdapter这样一个预定义适配器类，用于将 Product 对象列表与 ListView 列表视图进行绑定
public class CartAdapter extends ArrayAdapter<Product> {
    //上下文内容
    private Context context;
    //商品列表
    private List<Product> products;
    private zMysqlFoodHelper myCartSql;

    public CartAdapter(Context context, List<Product> products) {
        //0：默认资源
        super(context, 0, products);
        this.context = context;
        this.products = products;
        myCartSql = new zMysqlFoodHelper(context);
    }

    //适配器核心：拿到我的ListView视图（以下会对商品进行处理，才会将一整行处理好的商品返回给ListView）
    //重写 getView() 方法来自定义列表项的显示和交互逻辑
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView可重用的视图，指列表视图滑出屏幕的部分。其内容会被销毁，其内存空间可能会被重新利用（比如下面新划上来的视图利用）
        //这样可以减少内存的重复开辟和销毁，减少系统资源消耗
        //判断是否有可重用的视图
        //如果有可重用的视图，那就不为null，跳过if语句
        //如果没有可重用的视图，则需要通过 LayoutInflater 实例化一个新的视图对象
        if (convertView == null) {
            //实例化的这个视图对象，对应着 list_item_cart.xml 这个列表的具体item项（即一行商品） inflate表示一种映射关系
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_cart, parent, false);
        }
        //下面五行看似复杂，其实就是拿到 list_item_cart.xml 的商品列表每一行的五个元素，将其与java关联起来，以便使用代码操作元素
        TextView productNameTextView = convertView.findViewById(R.id.product_name_text_view);
        TextView productPriceTextView = convertView.findViewById(R.id.product_price_text_view);
        TextView productQuantityTextView = convertView.findViewById(R.id.product_quantity_text_view);
        ImageView increaseButton = convertView.findViewById(R.id.increase_button);
        ImageView decreaseButton = convertView.findViewById(R.id.decrease_button);

        //通过传进来的参数position(调用者的位置), 拿到指定位置的商品对象
        final Product product = getItem(position);
        if (product != null) {
            //3+2 两部分
            //如果商品不为空，那就get它的三个数据（商品名，单价，数量），以java的方式写入对应元素中
            productNameTextView.setText(product.getName());
            productPriceTextView.setText(String.format(Locale.getDefault(), "￥%.2f", product.getPrice()));
            productQuantityTextView.setText(String.valueOf(product.getQuantity()));

            //点击加号按钮，回调增加数量的函数
            increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //一个不更新数据库，一个不更新界面，玩得好啊，那就直接都写互补！！！
                    product.increaseQuantity();
                    myCartSql.add(product);
                    //监测数据集改动
                    notifyDataSetChanged();
                }
            });

            //点击减号按钮，回调减少数量的函数
            decreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //同理，你也来互补！！！
                    myCartSql.dec(product);
                    product.decreaseQuantity();
                    //商品数量都0了，那就是没有，直接移除了！
                    if (product.getQuantity() == 0) {
                        products.remove(product);
                    }
                    //监测数据集改动
                    notifyDataSetChanged();
                }
            });
        }

        //返回填充好数据的列表项视图 convertView
        return convertView;
    }
}
