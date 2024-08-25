package ecommerce.online.shops.app.repo;

import ecommerce.online.shops.app.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
