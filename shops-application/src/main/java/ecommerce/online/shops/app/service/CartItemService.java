package ecommerce.online.shops.app.service;

import ecommerce.online.shops.app.model.CartItem;

public interface CartItemService {

    void addCartItem(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
    CartItem getCartItem(Long cartId, Long productId);
}
