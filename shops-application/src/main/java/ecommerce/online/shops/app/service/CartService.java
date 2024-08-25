package ecommerce.online.shops.app.service;

import ecommerce.online.shops.app.model.Cart;

import java.math.BigDecimal;

public interface CartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
