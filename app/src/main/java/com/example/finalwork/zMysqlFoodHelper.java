package com.example.finalwork;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.finalwork.javabean.Cart;
import com.example.finalwork.javabean.Product;

import java.util.List;

public class zMysqlFoodHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="MYsqlite.db";

    //建表语句，创建cart表
    private static final String create_cart="create table cart(foodname varchar(32),foodprice varchar(32),quantity varchar(32))";
    private SQLiteDatabase sqLiteDatabase;

    //食物名，价格和数量最长32位
    public zMysqlFoodHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //直接调用上面的String(封装好语句的字符串)来exe执行该“创建语句”
        Log.d("1","看看我执行了没有？aaaaaa");
        sqLiteDatabase.execSQL(create_cart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("1","看看我执行了没有？bbbbbb");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS food");  // 删除原先的表
        sqLiteDatabase.execSQL(create_cart);  // 创建新表
    }

    //插入数据方法 add
    public void add (Product p){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //先判断好数据是否合法，再将账号密码打包成键值对 并 写入数据库
        long judge = -1;
        //1.重名情况
        //先拿到传进来的商品p的信息
        String foodname = p.getName();
        int quantity = p.getQuantity();
        Cursor point = db.query("cart",null,"foodname like ?",new String[]{foodname},null,null,null);
        if(foodname != null){
            //光标循环扫描
            while(point.moveToNext()){
                Log.d("1","重复！！！？");
                //拿到光标扫到的那一行的第一个数据foodname(索引从0开始，所以参数写0)
                String foodname1 = point.getString(0);
                //每次循环都判断etUsername是否与数据库中的账号重复,
                if(foodname.equals(foodname1)){
                    Log.d("3","数量为："+point.getString(2));
                    //重复了，修改数据库，让该商品的数量++
                    cv.put("foodname",foodname);
                    //该行第三列的数据，也就是数量，拿出来转为int进行++，然后再转回String写进去
                    String qt = (String)point.getString(2);
                    int newqt = Integer.parseInt(qt);
                    int newwqt = ++newqt;
                    String finalqt = String.valueOf(newwqt);
                    cv.put("quantity",finalqt);
                    //让foodname相对应的行的数据更新！
                    db.update("cart",cv,"foodname like ?",new String[]{foodname});
                    judge = 1;   //防止进入下一个语句
                }
            }
        }
        //2.不重名情况，即在上一个分支中没有重复，没有执行 food = 1 的赋值操作，要添加新商品到购物车
        if(judge == -1){
            cv.put("foodname",p.getName());
            cv.put("foodprice",p.getPrice());
            cv.put("quantity",p.getQuantity());
            db.insert("cart",null,cv);
        }
    }


    //减少数据方法 dec （在购物车页面调用）
    public void dec (Product p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //先判断好数据是否合法，再将账号密码打包成键值对 并 写入数据库
        long judge = -1;
        //1.重名情况
        //先拿到传进来的商品p的信息
        String foodname = p.getName();
        int quantity = p.getQuantity();
        Cursor point = db.query("cart", null, "foodname like ?", new String[]{foodname}, null, null, null);
        if (foodname != null ) {
            //光标循环扫描
            while (point.moveToNext()) {
                //拿到光标扫到的那一行的第一个数据foodname1(索引从0开始，所以参数写0)
                String foodname1 = point.getString(0);
                String thisquantity = point.getString(2);
                int intquantity = Integer.parseInt(thisquantity);
                //每次循环都判断foodname是否与数据库中的foodname1重复,还有目前的数量是否>0，重复且>0就数量--
                if (foodname.equals(foodname1) && intquantity>0 ) {
                    //已存在了，修改数据库，让该商品的数量--
                    cv.put("foodname", foodname);
                    //该行第三列的数据，也就是数量，拿出来转为int!!!
                    String qt = (String) point.getString(2);
                    int newqt = Integer.parseInt(qt);
                    int newwqt = --newqt;
                    String finalqt = String.valueOf(newwqt);
                    //转来转去，终于把最终的数据拿到手了！塞进去！！！
                    cv.put("quantity", finalqt);
                    //让foodname相对应的行的数据更新！
                    db.update("cart", cv, "foodname like ?", new String[]{foodname});
                    judge = 1;   //防止进入下一个语句
                }else if(foodname.equals(foodname1) && intquantity==0 ){
                    String[] whereArgs = {"0"};  //删除的数据可能不止一个，所以要用数组的形式
                    db.delete("cart", "quantity=?", whereArgs);
                    judge = 1;   //防止进入下一个语句
                }
            }
        }
        //2.不重名情况？说明购物车没有该商品，也删除不了什么
        if (judge == -1) {
            //直接空语句
        }
    }

    //计算总价方法     使用Cart类的getTotalPrice()就好了，不用再来操作数据库计算总价，有些麻烦，所以该部分内容注释掉
//    public double total (){
//        double total = 0;
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.query("cart", null, null, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                try {
//                    //拿到下标，游标指向相应下标位置即可拿到对应数据信息
//                    double price = cursor.getInt(cursor.getColumnIndexOrThrow("foodprice"));
//                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
//                    //总价追加计算
//                    total += quantity*price;
//                } catch (IllegalArgumentException e) {
//                    Log.e(TAG, "getColumnIndexOrThrow error: " + e.getMessage());
//                }
//
//            } while (cursor.moveToNext());
//        }
//        return total;
//    }
}
