package com.wishlist.domain.product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@ToString
@Document (collection = "products")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;
    private String code;
    private String description;
    private BigDecimal price;

    public static Product of(String code, String description, BigDecimal price) {
        return new Product(null, code, description, price);
    }

}
