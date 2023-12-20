package com.example.finalwork.javabean;

//商品类
public class Product {
    //三个属性：分别对应list每一行的三个TextView
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price,int quantity) {
        this.name = name;
        this.price = price;
        //刚开始用户点了一下加入购物车，数量为 1
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    //该商品数量++（在CartApapter中设置点击事件，点击了才回调此方法）
    public void increaseQuantity() {
        quantity++;
    }

    //该商品数量--（在CartApapter中设置点击事件，点击了才回调此方法）
    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }
}