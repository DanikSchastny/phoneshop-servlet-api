package com.es.phoneshop.logic;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.CartService.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ViewedProducts {
    private Queue<Product> viewedProductsList;
    private final int size = 3;


    public ViewedProducts(){
        this.viewedProductsList = new LinkedList<Product>();
    }

    public void delete(){
        if(this.viewedProductsList != null)
            this.viewedProductsList.remove();
        else
            throw new NoSuchElementException("No elements in queue");
    }

    public void add(Product item){
        if(item == null)
            throw new IllegalArgumentException("Invalid argument");
        if(this.viewedProductsList != null && this.viewedProductsList.size() < 3){
            this.viewedProductsList.add(item);
        }
    }
}
