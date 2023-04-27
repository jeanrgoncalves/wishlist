package com.wishlist.application.product;

import com.wishlist.application.wishlist.ProductDto;
import com.wishlist.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    @Operation(summary = "Lista todos os Produtos.")
    public ResponseEntity<List<ProductDto>> findAll() {
        var products = service.findAll();
        var productDtos = products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

}
