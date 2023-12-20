package com.example.finalwork;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalwork.javabean.Cart;
import com.example.finalwork.javabean.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class page2_chicken extends AppCompatActivity {

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
            R.drawable.chicken1,
            R.drawable.chicken2,
            R.drawable.chicken3,
            R.drawable.chicken4,
            R.drawable.chicken5,
            R.drawable.chicken6,
            R.drawable.chicken7,
            R.drawable.chicken8
    };
    private String[] foodnames = {
            "招牌杨氏炸鸡","炸鸡全家桶","无骨鸡柳","麦辣鸡腿堡","田园蔬菜堡","大大大鸡腿","黄金鸡排","肥宅快乐水"
    };
    private String[] foodprices = {
            "48","68","8","13","9","12","11","6"
    };
    private zMysqlFoodHelper myCartSql;
    private SQLiteDatabase sqLiteDatabase;
    //商品总数
    private int allnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2_chicken);

        //返回键
        TextView back = findViewById(R.id.chicken_back);
        //名称
        TextView n = findViewById(R.id.name);
        String name = n.getText().toString();

        mListView = findViewById(R.id.lv);
        mList = new ArrayList<>();
        //获取元素
        myCartSql = new zMysqlFoodHelper(this);
        //购物车初始化，此时里面是一个空数组
        cart = new Cart();

        //1.主视图mListView
        mListView = findViewById(R.id.lv);
        //2.数据的列表/数组，后面要用作适配器的第二个参数！
        mList = new ArrayList<>();
        for(int i = 0; i<8;i++){
            Map<String,Object> map = new HashMap<>();
            //每一行的item中有三个元素，通过map可以给他们取名，相当于一个列头元素
            map.put("img",imgs[i%8]);
            map.put("name",foodnames[i]);
            map.put("price",foodprices[i]);
            //每遍历完一条item，就添加到mList里面去
            mList.add(map);
        }
        //3.适配器，from mList
        mSimpleAdapter = new SimpleAdapter(this,           //上下文对象
                mList,R.layout.activity_page2_chicken1,           //自己写的列表，传到对应的item_xml的文件,注意是item 不是list
                new String[]{"img","name","price"},               //mList写入xml的数据，从这些键key
                new int[]{R.id.iv_img,R.id.tv_foodname,R.id.tv_foodprice});   //到对应的value(因为单纯传一个mList的类型数据，系统默认是不知道怎么对这些数据进行处理的)
        //适配器搞好了，接下来将适配器设置 给(to) 我们的主视图mListView,设置好所有图像和图片
        mListView.setAdapter(mSimpleAdapter);

        //显示总价按钮
        TextView totalshow = findViewById(R.id.totalshow);
        //去结算按钮
        TextView toCart = findViewById(R.id.toCart);

        //-------每次点击来这个页面，先显示原有购物车商品数量-----------
        //用数据库操作，从根本上保证了数据的时效性
        //比如我在另一个页面减少商品数量或删除商品，都有对数据库进行更改，所以跳回此页面的型数据也要根据数据库获取
        //相比之下，单纯+1 -1操作就可能导致各种变量作用域问题（何况还是跳转不同页面...）
        sqLiteDatabase = openOrCreateDatabase("MYsqlite.db", MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.query("cart", null, null, null, null, null, null);
        //先让全局变量初始化为0,等下要开始重新计算一遍了
        allnum = 0;
        if (cursor.moveToFirst()) {
            do {
                int quantity = 0;
                try {
                    //拿到下标，游标指向相应下标位置即可拿到对应数据信息
                    quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "getColumnIndexOrThrow error: " + e.getMessage());
                }
                //商品数量追加计算
                allnum += quantity;
            } while (cursor.moveToNext());
        }
        //最终的商品数量写入xml
        totalshow.setText(String.valueOf(allnum));
        // 关闭游标和数据库连接
        cursor.close();
        sqLiteDatabase.close();

        //-----------再设置点击“加入购物车”后的数据实时更新-----------
        //为整个列表设置点击事件，其实就是点击“加入购物车”
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //通过位置position,拿到该行的item所有数据
                Log.d("1","dgsdlgjs三国杀");
                Map<String,Object> map = mList.get(i);       //这里的i就是position，也就是对应列表中的位置（最上面是0，往下递增）
                //拿到该行数据的title(Object类型要转为String)
                String foodname = (String)map.get("name");
                String price = (String)map.get("price");
                int intprice = Integer.parseInt(price);
                int intquantity = 1;
                boolean judges = view.getId()==R.id.addToCart;

                Toast.makeText(page2_chicken.this,"您点击了"+foodname+",",Toast.LENGTH_SHORT).show();

                //点击后 回调add函数
                Product p = new Product(foodname, intprice,intquantity);
                Log.d("1",p.getName());
                Log.d("1", String.valueOf(p.getPrice()));
                Log.d("1", String.valueOf(p.getQuantity()));
                myCartSql.add(p);

                //add增加后，数据库更新了，重新遍历数据库拿到新的数量
                sqLiteDatabase = openOrCreateDatabase("MYsqlite.db", MODE_PRIVATE, null);
                Cursor cursor = sqLiteDatabase.query("cart", null, null, null, null, null, null);
                //先让全局变量初始化为0,等下要开始重新计算一遍了
                allnum = 0;
                if (cursor.moveToFirst()) {
                    do {
                        int quantity = 0;
                        try {
                            //拿到下标，游标指向相应下标位置即可拿到对应数据信息
                            quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                        } catch (IllegalArgumentException e) {
                            Log.e(TAG, "getColumnIndexOrThrow error: " + e.getMessage());
                        }
                        //商品数量追加计算
                        allnum += quantity;
                    } while (cursor.moveToNext());
                }
                //最终的商品数量写入xml
                totalshow.setText(String.valueOf(allnum));
                // 关闭游标和数据库连接
                cursor.close();
                sqLiteDatabase.close();
                //顺便更新商品数量
                totalshow.setText(String.valueOf(allnum));
            }
        });


        //去结算跳转
        toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击跳转到购物车，需判断购物车是否为空，来决定跳转到哪一个xml页面
                sqLiteDatabase = openOrCreateDatabase("MYsqlite.db", MODE_PRIVATE, null);
                Cursor cursor = sqLiteDatabase.query("cart", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        int quantity = 0;
                        try {
                            //拿到下标，游标指向相应下标位置即可拿到对应数据信息
                            quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                        } catch (IllegalArgumentException e) {
                            Log.e(TAG, "getColumnIndexOrThrow error: " + e.getMessage());
                        }
                        if (quantity !=0) {
                            //但凡找到一个数量不为0的商品都说明购物车不为空，需跳到 page3_Cart
                            Intent to_p3cart = new Intent(page2_chicken.this, page3_Cart.class);
                            to_p3cart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            //打包这个页面的数据，传到结算页面，等下返回能跳回来这个页面
                            Bundle bundle = new Bundle();
                            bundle.putString("name", name);
                            to_p3cart.putExtras(bundle);

                            startActivity(to_p3cart);
                            return;
                        }
                    } while (cursor.moveToNext());
                }
                // 关闭游标和数据库连接
                cursor.close();
                sqLiteDatabase.close();
                //前面的quantity都为0,没有return,所以要执行以下代码，跳转到 page3
                Intent to_p3 = new Intent(page2_chicken.this, page3.class);
                Log.d("1","3333333333333333333？");
                startActivity(to_p3);
            }
        });

        //返回跳转
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_p2 = new Intent(page2_chicken.this,page2.class);
                back_p2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(back_p2);
            }
        });
    }

}