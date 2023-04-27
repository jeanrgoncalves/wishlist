package com.wishlist.application.wishlist;

import com.wishlist.domain.wishlist.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {

    private String id;
    private String clientId;
    private List<ProductDto> products;

    public static WishlistDto from(Wishlist wishlist) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (var product : wishlist.getProducts())
            productDtos.add(ProductDto.from(product));

        return new WishlistDto(wishlist.getId(), wishlist.getClientId(), productDtos);
    }

}
