package com.wishlist.domain.wishlist;

import com.wishlist.domain.product.Product;
import com.wishlist.exception.WishlistMaxSizeException;
import com.wishlist.util.WishlistUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "wishlists")
@Slf4j
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
            products = new ArrayList<>(); //lista mutável para utilizar nos testes
            products.add(product);
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
        if (products.removeIf(p -> p.getId().equals(productId)))
            log.info("Produto com id {} removido da Wishlist do cliente com id {}", productId, clientId);
        else
            log.info("Produto com id {} não foi encontrado na Wishlist do cliente com id {}", productId, clientId);
    }

    public Boolean checkProductExists(String productId) {
        if (products.stream().anyMatch(p -> p.getId().equals(productId))) {
            log.info("Produto com id {} encontrado na Wishlist do cliente com id {}", productId, clientId);
            return true;
        } else {
            log.info("Produto com id {} não encontrado na Wishlist do cliente com id {}", productId, clientId);
            return false;
        }
    }

}
