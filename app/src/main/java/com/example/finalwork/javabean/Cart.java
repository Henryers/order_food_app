package com.example.finalwork.javabean;

import java.util.ArrayList;
import java.util.List;

//Cart 类是一个包含 Product 对象的数组/列表类，其中的每个元素都是一个 Product 对象
public class Cart {
    //属性就一个，Product类作为对象的列表类，以下其他部分为相关方法
    private List<Product> products;
    //初始化该类，就是给列表分配一个内存空间
    public Cart() {
        products = new ArrayList<>();
    }
    //增加商品方法
    public void addProduct(Product product) {
        products.add(product);
    }
    //移除商品方法
    public void removeProduct(Product product) {
        products.remove(product);
    }
    //拿到整个products列表
    public List<Product> getProducts() {
        return products;
    }
    //计算商品总价
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product product : products) {
            //每个商品的单价*购买数量，追加到总价里面
            totalPrice += product.getPrice() * product.getQuantity();
        }
        return totalPrice;
    }
}
