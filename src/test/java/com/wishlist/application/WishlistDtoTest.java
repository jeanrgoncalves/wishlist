package com.wishlist.application;

import com.wishlist.application.wishlist.WishlistDto;
import com.wishlist.domain.product.Product;
import com.wishlist.domain.wishlist.Wishlist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

public class WishlistDtoTest {

    @Test
    void from() {
        var product = Product.builder()
                .id(UUID.randomUUID().toString())
                .code("10")
                .description("TV LED LG 501 4K")
                .price(BigDecimal.valueOf(2800))
                .build();

        var wishlist = Wishlist.builder()
                .id(UUID.randomUUID().toString())
                .clientId(UUID.randomUUID().toString())
                .products(Collections.singletonList(product))
                .build();

        var dto = WishlistDto.from(wishlist);

        Assertions.assertEquals(dto.getId(), wishlist.getId());
        Assertions.assertEquals(dto.getClientId(), wishlist.getClientId());
        Assertions.assertEquals(dto.getProducts().size(), wishlist.getProducts().size());

        var productDto = dto.getProducts().get(0);

        Assertions.assertEquals(productDto.getId(), product.getId());
        Assertions.assertEquals(productDto.getCode(), product.getCode());
        Assertions.assertEquals(productDto.getDescription(), product.getDescription());
        Assertions.assertEquals(productDto.getPrice(), product.getPrice());
    }

}
