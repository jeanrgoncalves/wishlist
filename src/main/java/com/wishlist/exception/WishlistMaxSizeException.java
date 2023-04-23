package com.wishlist.exception;

import com.wishlist.util.WishlistUtil;

public class WishlistMaxSizeException extends RuntimeException {

    public WishlistMaxSizeException() {
        super("A Wishlist já atingiu seu tamanho máximo de " + WishlistUtil.WISHLIST_MAX_SIZE + " produtos.");
    }

}
