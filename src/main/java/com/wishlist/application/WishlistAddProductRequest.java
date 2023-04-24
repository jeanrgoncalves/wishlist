package com.wishlist.application;

import lombok.Data;

@Data
public class WishlistAddProductRequest {

    private String productId;
    private String clientId;

}
