package com.es.phoneshop.CartService;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Cart {
    private List<CartItem> cartList;

    public Cart() {
        cartList = new ArrayList<>();
    }

    public void save(CartItem cartItem) {
        if(cartItem == null)
            throw new IllegalArgumentException("Invalid product to add");
        cartList.add(cartItem);
    }

    public boolean containProduct(CartItem cartItem){
        return this.cartList.stream().filter(x->x.getProduct().equals(cartItem.getProduct())).findFirst().isPresent();
    }
    public boolean find(CartItem cartItem){
        return this.cartList.stream().filter(x->x.equals(cartItem)).findFirst().isPresent();
    }
    public void delete(CartItem cartItem) {
        if(this.find(cartItem))
            throw new IllegalArgumentException("Cant find such cartItem");
        cartList.remove(cartItem);
    }

    public List<CartItem> getCartItemList() {
        return cartList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        if(cartItemList != null)
        this.cartList = cartItemList;
    }
}
