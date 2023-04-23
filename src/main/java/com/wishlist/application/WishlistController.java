package com.wishlist.application;

import com.wishlist.domain.product.Product;
import com.wishlist.domain.product.ProductRepository;
import com.wishlist.exception.WishlistMaxSizeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final ProductRepository productRepository;

    @GetMapping
    public Product teste() {
        /*var product = productRepository.findById("64454e3bc0e188e8df242d90").get();
        return product;*/

        throw new WishlistMaxSizeException();
    }

}
