package com.example.testwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testwork.javabean.User;

public class MYsqliteopenhelper extends SQLiteOpenHelper {
    private static final String DB_NAME="MYsqlite.db";

    //建表语句
    private static final String create_user="create table users(name varchar(32),password varchar(32))";
    //账号密码最长32位
    public MYsqliteopenhelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //直接调用上面的String(封装好语句的字符串)来创建语句  -- 其实是语句太长了，所以分开来写
        sqLiteDatabase.execSQL(create_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //实现注册功能的方法
    public long register(User u){   //u 里面有输入框的账号和密码
        //拿到数据库
        SQLiteDatabase db = getWritableDatabase();
        //用cv存一个键值对
        ContentValues cv = new ContentValues();
        //默认写入数据库不成功，除非数据合法-->下面if语句执行，返回不是-1的数
        long users = -1;
        //Log.d("..","看看我执行了没有？");
        //先判断好数据是否合法，再将账号密码打包成键值对 并 写入数据库
        //神仙，长度为0 居然是不为null的... 服了... 一个晚上又没了...(用isEmpty才能对对长度为0的字符串做出判断！)
        if(!u.getName().isEmpty() && !u.getPassword().isEmpty()){
            cv.put("name",u.getName());
            cv.put("password",u.getPassword());
            //向数据库里面插入键值对
            users = db.insert("users",null,cv);
            return users;   //if执行了，返回不是-1的数
        }
        //Log.d("...","看看我执行了没有？");
        return users;  //if没执行，返回-1
    }

    //实现登录功能的方法
    public boolean login(String name,String password){
        SQLiteDatabase db1 = getWritableDatabase();
        boolean result = false;
        //在数据库中查找用户名是否存在，并返回一个对象(类似一个左边的光标，指向右边数据库的某一整行数据，即一个账号密码)
        Cursor users = db1.query("users",null,"name like ?",new String[]{name},null,null,null);
        //如果返回不为空，说明找到了，接下来要验证对应的密码是否正确
        Log.d(".","看看我执行了没有？");
        if(users != null){
            //光标循环扫描
            while(users.moveToNext()){
                //-----------------------------
                //-------  先tm 删了吧！--------------
                String[] whereArgs = {""};  //删除的数据可能不止一个，所以要用数组的形式
                db1.delete("users", "name=?", whereArgs);
                db1.delete("users", "password=?", whereArgs);
                //-----------------------------

                //拿到光标扫到的那一行的第二个数据password(索引从0开始，所以参数写1)
                String password1 = users.getString(1);
                //输入进来的password和数据库里的password1进行比对，返回的对错结果交给result
                result = password1.equals(password);
                return result;
            }
        }
        Log.d(".","看看我执行了没有？");
        //没进入if语句才会执行到这里，所以此时为账号不存在的情况
        return false;
    }

    //实现注销功能的方法
    public boolean logout(String name,String password){
        //拿到数据库的信息
        SQLiteDatabase db2 = getWritableDatabase();
        boolean result = false;
        //在数据库中查找用户名是否存在，并返回一个对象(类似一个左边的光标，指向右边数据库的某一整行数据，即一个账号密码)
        Cursor users = db2.query("users",null,"name like ?",new String[]{name},null,null,null);
        //对信息进行判断
        //如果当前name存在，与输入的password也对应的话，那么就注销成功，把该行数据从数据库中移除
        if(users != null){
            //光标循环扫描
            while(users.moveToNext()){
                //拿到光标扫到的那一行的第二个数据password(索引从0开始，所以参数写1)
                String password1 = users.getString(1);
                //输入进来的password和数据库里的password1进行比对，返回的对错结果交给result
                result = password1.equals(password);
                if(result){
                    //数据库确实有此账号密码，且数据对应，那么删了吧
                    String[] namearr = {name};
                    String[] passwordarr = {password};
                    db2.delete("users", "name=?", namearr);
                    db2.delete("users", "password=?", passwordarr);
                    return result;
                }
            }
        }
        return result;
    }
}
