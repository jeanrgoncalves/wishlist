package com.wishlist.domain.wishlist;

import com.wishlist.domain.product.Product;
import com.wishlist.exception.WishlistMaxSizeException;
import com.wishlist.util.WishlistUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class WishlistTest {

    private Wishlist getNewWishlist() {
        return Wishlist.builder()
                .id("6445ed2fc0e188e8df242d96")
                .clientId("6445ec4ac0e188e8df242d94")
                .build();
    }

    private Product getNewProduct() {
        return Product.builder()
                .id("8455ec4ac0e188e8df242c55")
                .code("25")
                .description("TV LED LG 50' 4K")
                .price(BigDecimal.valueOf(2800))
                .build();
    }

    @Test
    void addProductSuccess() {
        var wishlist = getNewWishlist();
        var product = getNewProduct();

        wishlist.addProduct(product);

        Assertions.assertEquals(wishlist.getProducts().size(), 1);
    }

    @Test
    void addExistingProduct() {
        var wishlist = getNewWishlist();
        var product = getNewProduct();

        wishlist.addProduct(product);
        wishlist.addProduct(product);

        Assertions.assertEquals(wishlist.getProducts().size(), 1);
    }

    @Test
    void exceedLimitOfProducts() {
        var wishlist = getNewWishlist();

        for (int i = 0; i < WishlistUtil.WISHLIST_MAX_SIZE; i++) {
            wishlist.addProduct(Product.builder()
                    .id(UUID.randomUUID().toString())
                    .code("1")
                    .description("Product Test")
                    .price(BigDecimal.valueOf(10))
                    .build());
        }

        Assertions.assertThrows(WishlistMaxSizeException.class, () -> wishlist.addProduct(getNewProduct()));
    }

    @Test
    void removeExistingProduct() {
        var wishlist = getNewWishlist();
        var product = getNewProduct();
        wishlist.addProduct(product);

        wishlist.removeProduct("8455ec4ac0e188e8df242c55");

        Assertions.assertTrue(wishlist.getProducts().isEmpty());
    }

    @Test
    void removeNotExistingProduct() {
        var wishlist = getNewWishlist();
        var product = getNewProduct();
        wishlist.addProduct(product);

        wishlist.removeProduct("6287ec4ac0e188e8df242f78");

        Assertions.assertEquals(wishlist.getProducts().size(), 1);
    }

    @Test
    void checkProductExistsTrue() {
        var wishlist = getNewWishlist();
        var product = getNewProduct();
        wishlist.addProduct(product);

        Assertions.assertTrue(wishlist.checkProductExists("8455ec4ac0e188e8df242c55"));
    }

    @Test
    void checkProductExistsFalse() {
        var wishlist = getNewWishlist();
        var product = getNewProduct();
        wishlist.addProduct(product);

        Assertions.assertFalse(wishlist.checkProductExists("6287ec4ac0e188e8df242f78"));
    }

}
