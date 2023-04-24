package com.wishlist.domain.wishlist;

import com.wishlist.domain.product.Product;
import com.wishlist.exception.WishlistMaxSizeException;
import com.wishlist.util.WishlistUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document(collection = "wishlists")
@Slf4j
@Builder
@Getter
public class Wishlist {

    @Id
    private String id;

    @Indexed(unique = true)
    private String clientId;
    private List<Product> products;

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void addProduct(Product product) {
        if (products == null) {
            products = Collections.singletonList(product);
            return;
        }

        if (products.size() == WishlistUtil.WISHLIST_MAX_SIZE) {
            log.error("A Wishlist do cliente com id {} já atingiu seu tamanho máximo de " + WishlistUtil.WISHLIST_MAX_SIZE + " produtos", clientId);
            throw new WishlistMaxSizeException();
        }

        if (products.stream().anyMatch(p -> p.equals(product))) {
            return;
        }

        products.add(product);
    }

    public void removeProduct(String productId) {
        products.removeIf(p -> p.getId().equals(productId));
        log.info("Produto com id {} removido da Wishlist do cliente com id {}", productId, clientId);
    }

}
