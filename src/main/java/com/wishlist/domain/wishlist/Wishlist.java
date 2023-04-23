package com.wishlist.domain.wishlist;

import com.wishlist.domain.product.Product;
import com.wishlist.exception.WishlistMaxSizeException;
import com.wishlist.util.WishlistUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String id;
    private String clientId;
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (products.size() == WishlistUtil.WISHLIST_MAX_SIZE)
            throw new WishlistMaxSizeException();

        if (products.stream().anyMatch(p -> p.equals(product)))
            return;

        products.add(product);
    }

}
