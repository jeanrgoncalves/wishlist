package com.wishlist.application;

import com.wishlist.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private String code;
    private String description;
    private BigDecimal price;

    public static ProductDto from(Product product) {
        return new ProductDto(product.getId(), product.getCode(), product.getDescription(), product.getPrice());
    }
}
