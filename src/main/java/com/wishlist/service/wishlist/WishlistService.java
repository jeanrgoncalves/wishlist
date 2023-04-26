package com.wishlist.service.wishlist;

import com.wishlist.domain.wishlist.Wishlist;

public interface WishlistService {

    Wishlist addProduct(String productId, String clientId);

    void removeProduct(String productId, String clientId);

    Wishlist findByClientId(String clientId);

    Boolean checkProductExists(String productId, String clientId);

}
