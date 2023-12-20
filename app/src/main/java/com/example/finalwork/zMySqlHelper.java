package com.example.finalwork;

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

import com.example.finalwork.javabean.User;

import androidx.annotation.Nullable;

public class zMySqlHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="MYsqlite.db";

    //建表语句
    private static final String create_user="create table users(name varchar(32),password varchar(32))";
    //账号密码最长32位
    public zMySqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 2);
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
        //默认写入账号已存在，除非是新账号-->下面第二个if语句执行
        long users = -1;
        //先判断好数据是否合法，再将账号密码打包成键值对 并写入数据库
        //1.重名情况
        String etUsername = u.getName();
        Cursor point = db.query("users",null,"name like ?",new String[]{etUsername},null,null,null);
        if(etUsername != null){
            while(point.moveToNext()){                //光标循环扫描
                //拿到光标扫到的那一行的第一个数据username(索引从0开始，所以参数写0)
                String username1 = point.getString(0);
                if(etUsername.equals(username1)){     //每次循环都判断etUsername是否与数据库中的账号重复
                    return users;                     //重复了，肯定不合法，别执行下面的代码了，带上-1退出吧！
                }
            }
        }
        //2.不为空的合法情况（上面没重复，没return，来到这个分支）
        //长度为0 居然是不为null的...(用isEmpty才能对长度为0的字符串做出判断！)
        if(!u.getName().isEmpty() && !u.getPassword().isEmpty()){
            cv.put("name",u.getName());
            cv.put("password",u.getPassword());
            //向数据库里面插入键值对
            users = db.insert("users",null,cv);
            users = 1;
        }
        //上面的if有执行的话返回1，没执行的话依然返回-1
        return users;
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
                String username1 = users.getString(0);
                String password1 = users.getString(1);
                //输入进来的name和数据库里的username1进行比对，password和数据库里的password1进行比对
                //两个都要同时满足，返回的对错结果交给result
                result = username1.equals(name) && password1.equals(password);
                if(result){
                    //数据库确实有此账号密码，且数据对应，那么删了吧
                    //注意只能删除对应账号的数据行，不能删除密码对应的所有数据行，否则会把其他人相同密码的数据行也删除
                    String[] namearr = {name};
                    db2.delete("users", "name=?", namearr);
                    return result;
                }
            }
        }
        return result;
    }
}
