package com.wishlist.application;

import com.wishlist.domain.product.Product;
import com.wishlist.domain.wishlist.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {

    private String id;
    private String clientId;
    private List<Product> products;

    public static WishlistDto from(Wishlist wishlist) {
        return new WishlistDto(wishlist.getId(), wishlist.getClientId(), wishlist.getProducts());
    }

}
