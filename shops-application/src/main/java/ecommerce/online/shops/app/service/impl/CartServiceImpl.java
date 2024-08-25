package ecommerce.online.shops.app.service.impl;

import ecommerce.online.shops.app.model.Cart;
import ecommerce.online.shops.app.repo.CartItemRepository;
import ecommerce.online.shops.app.repo.CartRepository;
import ecommerce.online.shops.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    @Override
    public Cart getCart(Long id) {
        var cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        var totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }
    @Transactional
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
        return cart.getTotalAmount();
    }
    @Override
    public Long initializeNewCart() {
        var newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }
}
