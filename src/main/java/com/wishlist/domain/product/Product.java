package com.wishlist.domain.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Document (collection = "products")
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    private String id;
    private String code;
    private String description;
    private BigDecimal price;

}
