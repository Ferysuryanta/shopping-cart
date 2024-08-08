package ecommerce.online.shops.app.controller;

import ecommerce.online.shops.app.respponse.ApiResponse;
import ecommerce.online.shops.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        var products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        var product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("success", product));
    }
}
