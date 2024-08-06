package ecommerce.online.shops.app.controller;

import ecommerce.online.shops.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping()
public class ProductController {

    private final ProductService productService;
}
