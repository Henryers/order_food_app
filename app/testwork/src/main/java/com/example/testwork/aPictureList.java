package com.example.testwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testwork.javabean.Cart;
import com.example.testwork.javabean.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class aPictureList extends AppCompatActivity {

    private Cart cart;
    //准备三件套：列表，适配器，具体每行数据模板
    //1.列表
    private ListView mListView;
    //2.适配器
    private SimpleAdapter mSimpleAdapter;
    //3.数据模板mList
    private List<Map<String, Object>> mList;   //Object宽泛类型，因为后面可能传图片或文字，数据类型不统一！
    //map里面的img数组
    private int[] imgs = {
            R.drawable.front,
            R.drawable.appicon,
            R.drawable.ic_launcher_background
    };
    private String[] foodnames = {
            "鸡","薯","肉","粿条","饭","拌面","日料","猪"
    };
    private String[] foodprices = {
            "66","43","23","69","63","55","2","8"
    };
    private zMysqlFoodHelper myCartSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicture_list);

        mListView = findViewById(R.id.lv);
        mList = new ArrayList<>();
        //获取元素
        myCartSql = new zMysqlFoodHelper(this);
        //购物车初始化，此时里面是一个空数组
        cart = new Cart();

//        Log.d("1","看看我执行了没有？");
//        mSimpleAdapter = new SimpleAdapter(this, mList, R.layout.activity_apicture_item,
//                new String[]{"name", "price"},
//                new int[]{R.id.tv_foodname, R.id.tv_foodprice});
//
//        Log.d("1","看看我执行了没有？");
//        mSimpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
//            @Override
//            public boolean setViewValue(View view, Object data, String textRepresentation) {
//                Log.d("1","看看我执行了没有？");
//                if (view.getId() == R.id.addToCart) {
//                    Log.d("1","看看我执行了没有？");
//                    TextView addToCartTextView = (TextView) view;
//                    addToCartTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.d("1","看看我执行了没有？");
//                            int position = (int) v.getTag();
//                            Map<String, Object> map = mList.get(position);
//                            String foodname = (String) map.get("name");
//                            String price = (String) map.get("price");
//                            int intprice = Integer.parseInt(price);
//                            int intquantity = 1;
//
//                            Toast.makeText(aPictureList.this, "你点击了" + position + ": " + foodname + ", " + price, Toast.LENGTH_SHORT).show();
//
//                            Product p = new Product(foodname, intprice, intquantity);
//                            myCartSql.add(p);
//
//                            // 更新数据或界面等操作
//                            // ...
//                        }
//                    });
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        mListView.setAdapter(mSimpleAdapter);



        //1.主视图mListView
        mListView = findViewById(R.id.lv);
        //2.数据的列表/数组，后面要用作适配器的第二个参数！
        mList = new ArrayList<>();
        for(int i = 0; i<8;i++){
            Map<String,Object> map = new HashMap<>();
            //每一行的item中有三个元素，通过map可以给他们取名，相当于一个列头元素
            map.put("img",imgs[i%3]);  //图片不够，余数来凑！！！
            map.put("name",foodnames[i]);
            map.put("price",foodprices[i]);
            //每遍历完一条item，就添加到mList里面去
            mList.add(map);
        }
        //3.适配器，from mList
        mSimpleAdapter = new SimpleAdapter(this,                  //上下文对象
                mList,R.layout.activity_apicture_item,                   //自己写的列表，传到对应的item_xml的文件
                new String[]{"img","name","price"},                      //mList写入xml的数据，从这些键key
                new int[]{R.id.iv_img,R.id.tv_foodname,R.id.tv_foodprice});   //到对应的value(因为单纯传一个mList的类型数据，系统默认是不知道怎么对这些数据进行处理的)
        //适配器搞好了，接下来将适配器设置 给(to) 我们的主视图mListView,设置好所有图像和图片
        mListView.setAdapter(mSimpleAdapter);

        //去结算按钮
        TextView toCart = findViewById(R.id.toCart);

        //为整个列表设置点击事件(写在适配器里吧...)
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //通过位置position,拿到该行的item所有数据
                Map<String,Object> map = mList.get(i);       //这里的i就是position，也就是对应列表中的位置（最上面是0，往下递增）
                //拿到该行数据的title(Object类型要转为String)
                String foodname = (String)map.get("name");
                String price = (String)map.get("price");
                int intprice = Integer.parseInt(price);
                int intquantity = 1;
                boolean judges = view.getId()==R.id.addToCart;
                Log.d("1",String.valueOf(view.getId()));
                Log.d("1",String.valueOf(R.id.addToCart));
                Log.d("1",String.valueOf(findViewById(R.id.addToCart)));
                Log.d("1",String.valueOf(view));
                Log.d("1",String.valueOf(judges));

//                if(view==findViewById(R.id.addToCart)){
                Toast.makeText(aPictureList.this,"你点击了"+i+foodname+","+price,Toast.LENGTH_SHORT).show();

                //-------------------------
                Log.d("1","看看我执行了没有？");


                //点击后 回调add函数
                Product p = new Product(foodname, intprice,intquantity);
                Log.d("1",p.getName());
                Log.d("1", String.valueOf(p.getPrice()));
                Log.d("1", String.valueOf(p.getQuantity()));
                myCartSql.add(p);
//                }
//
//
//
//
////                //顺便先加入购物车
////                Product product = new Product(foodname, intprice);
////                //商品加入购物车
////                for (Product eachproduct : cart.getProducts()){
////                    //如果该商品已经加入购物车了，那么数量++就行！
////                    if(eachproduct.getName() == product.getName() && eachproduct.getPrice() == product.getPrice()){
////                        product.increaseQuantity();
////                        //监测数据集改动
////                        mSimpleAdapter.notifyDataSetChanged();
////                    }else{    //否则商品不存在，要加入商品列表中
////                        cart.addProduct(product);
////                    }
////                }
////                cart.addProduct(product);
////                //每点击一次，数量++
////                product.increaseQuantity();
////                //监测数据集改动
////                mSimpleAdapter.notifyDataSetChanged();
////                Log.d("1","看看我执行了没有？");
////                Log.d("2",product.getName());
////                Log.d("2", String.valueOf(product.getPrice()));
////                Log.d("2", String.valueOf(product.getQuantity()));
            }
        });


        //去结算跳转
        toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_cart = new Intent(aPictureList.this,CartActivity.class);
                startActivity(to_cart);
            }
        });
    }
}