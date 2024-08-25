package ecommerce.online.shops.app.service.impl;

import ecommerce.online.shops.app.model.Cart;
import ecommerce.online.shops.app.model.CartItem;
import ecommerce.online.shops.app.repo.CartItemRepository;
import ecommerce.online.shops.app.repo.CartRepository;
import ecommerce.online.shops.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public Cart getCart(Long id) {
        var cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        var totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        var cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        var cart = getCart(id);
//        return cart.getItems()
//                .stream()
//                .map(CartItem::getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return cart.getTotalAmount();
    }
}
