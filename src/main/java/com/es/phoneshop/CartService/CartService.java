package com.es.phoneshop.CartService;

import com.es.phoneshop.model.product.ArrayListProductDao;
import java.util.Optional;

public class CartService {
    private Cart cart;

    public CartService(Cart cart){
        this.cart = cart;
    }

    public void add(CartItem item){
        if(this.cart.containProduct(item)){
            Optional<CartItem> existingProduct = this.cart.getCartItemList().stream().filter(x->x.getProduct().equals(item.getProduct())).findFirst();
            this.cart.delete(existingProduct.get());
            int newQuantity = existingProduct.get().getQuantity() + item.getQuantity();

            if(newQuantity > existingProduct.get().getProduct().getStock())
                throw new IllegalArgumentException("Invalid stock");
            else{
                existingProduct.get().setQuantity(newQuantity);
                this.cart.save(existingProduct.get());
            }
        }
        else
            if(item.getQuantity() > item.getProduct().getStock())
                throw new IllegalArgumentException("Invalid stock");

            this.cart.save(item);
    }

    public Cart getCart(){
        return this.cart;
    }
}
