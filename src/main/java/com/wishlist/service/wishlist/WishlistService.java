package com.wishlist.service.wishlist;

import com.wishlist.domain.wishlist.Wishlist;

public interface WishlistService {

    Wishlist addProduct(String productId, String clientId);

}
