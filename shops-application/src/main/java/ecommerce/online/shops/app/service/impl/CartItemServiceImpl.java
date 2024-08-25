package ecommerce.online.shops.app.service.impl;

import ecommerce.online.shops.app.exception.ResourceNotFoundException;
import ecommerce.online.shops.app.model.CartItem;
import ecommerce.online.shops.app.repo.CartItemRepository;
import ecommerce.online.shops.app.repo.CartRepository;
import ecommerce.online.shops.app.service.CartItemService;
import ecommerce.online.shops.app.service.CartService;
import ecommerce.online.shops.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final CartRepository cartRepository;
    @Override
    public void addCartItem(Long cartId, Long productId, int quantity) {
        /**
            1. Get Cart
            2. Get Product
            3. Check if the product already in the cart
            4. If yes, then increase the quantity with the requested quantity
            5. If no, the initiate a new CartItem entry.
         */
        var cart = cartService.getCart(cartId);
        var product = productService.getProductById(productId);
        var cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        var cart = cartService.getCart(productId);
        var itemRemove = getCartItem(cartId, productId);
        cart.removeItem(itemRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        var cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        var totalAmount = cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        var cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
