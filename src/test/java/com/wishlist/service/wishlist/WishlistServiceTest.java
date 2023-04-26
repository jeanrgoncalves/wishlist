package com.wishlist.service.wishlist;

import com.wishlist.domain.client.ClientRepository;
import com.wishlist.domain.product.Product;
import com.wishlist.domain.product.ProductRepository;
import com.wishlist.domain.wishlist.Wishlist;
import com.wishlist.domain.wishlist.WishlistRepository;
import com.wishlist.exception.ClientNotFoundException;
import com.wishlist.exception.ProductNotFoundException;
import com.wishlist.exception.WishlistNotFoundException;
import com.wishlist.service.client.ClientService;
import com.wishlist.service.client.ClientServiceImpl;
import com.wishlist.service.product.ProductService;
import com.wishlist.service.product.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class WishlistServiceTest {

    private ClientService clientService;
    private ProductService productService;
    private WishlistRepository wishlistRepository;
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    void setup() {
        clientService = Mockito.mock(ClientServiceImpl.class);
        productService = Mockito.mock(ProductServiceImpl.class);
        wishlistRepository = Mockito.mock(WishlistRepository.class);
        wishlistService = new WishlistServiceImpl(clientService, productService, wishlistRepository);
    }

    @Test
    void addProductSuccess() {
        String clientId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        var product = Product.builder()
                .id(productId)
                .code("10")
                .description("TV LED LG 501 4K")
                .price(BigDecimal.valueOf(2800))
                .build();

        Mockito.when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.empty());
        Mockito.when(productService.findProductById(productId)).thenReturn(product);

        var result = wishlistService.addProduct(productId, clientId);

        Mockito.verify(wishlistRepository).save(Mockito.any(Wishlist.class));
        Assertions.assertEquals(result.getProducts().size(), 1);
    }

    @Test
    void addProductClientNotFound() {
        var clientRepository = Mockito.mock(ClientRepository.class);
        var realClientService = new ClientServiceImpl(clientRepository);
        wishlistService = new WishlistServiceImpl(realClientService, productService, wishlistRepository);

        String clientId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ClientNotFoundException.class, () -> wishlistService.addProduct(productId, clientId));
    }

    @Test
    void addProductProductNotFound() {
        var productRepository = Mockito.mock(ProductRepository.class);
        var realProductService = new ProductServiceImpl(productRepository);
        wishlistService = new WishlistServiceImpl(clientService, realProductService, wishlistRepository);

        String clientId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class, () -> wishlistService.addProduct(productId, clientId));
    }

    @Test
    void notFoundWishlistByClientIdCreateNew() {
        String clientId = UUID.randomUUID().toString();

        Mockito.when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        var wishlist = wishlistService.createOrFindWishlistByClientId(clientId);

        Assertions.assertNull(wishlist.getId());
        Assertions.assertEquals(wishlist.getClientId(), clientId);
    }

    @Test
    void findWishlistByClientId() {
        String clientId = UUID.randomUUID().toString();

        var product = Product.builder()
                .id(UUID.randomUUID().toString())
                .code("10")
                .description("TV LED LG 501 4K")
                .price(BigDecimal.valueOf(2800))
                .build();

        var wishlistReturnedByDB = Wishlist.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .products(Collections.singletonList(product))
                .build();

        Mockito.when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistReturnedByDB));

        var wishlist = wishlistService.createOrFindWishlistByClientId(clientId);

        Assertions.assertEquals(wishlist, wishlistReturnedByDB);
    }

    @Test
    void removeProductWishlistNotFound() {
        String clientId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        Mockito.when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        Assertions.assertThrows(WishlistNotFoundException.class, () -> wishlistService.removeProduct(productId, clientId));
    }

    @Test
    void removeProductAndDeleteWishlist() {
        String clientId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        var product = Product.builder()
                .id(productId)
                .code("10")
                .description("TV LED LG 501 4K")
                .price(BigDecimal.valueOf(2800))
                .build();

        var products = new ArrayList<Product>();
        products.add(product);
        var wishlistReturnedByDB = Wishlist.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .products(products)
                .build();

        Mockito.when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistReturnedByDB));

        wishlistService.removeProduct(productId, clientId);

        Mockito.verify(wishlistRepository).deleteById(wishlistReturnedByDB.getId());
    }

    @Test
    void removeProductAndSaveWishlist() {
        String clientId = UUID.randomUUID().toString();
        String productId = UUID.randomUUID().toString();

        var product1 = Product.builder()
                .id(productId)
                .code("10")
                .description("TV LED LG 501 4K")
                .price(BigDecimal.valueOf(2800))
                .build();

        var product2 = Product.builder()
                .id(UUID.randomUUID().toString())
                .code("55")
                .description("iPhone 13 Pro Max")
                .price(BigDecimal.valueOf(8500))
                .build();

        var products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);
        var wishlistReturnedByDB = Wishlist.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .products(products)
                .build();

        Mockito.when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistReturnedByDB));

        wishlistService.removeProduct(productId, clientId);

        Mockito.verify(wishlistRepository).save(wishlistReturnedByDB);
    }

    @Test
    void executeFindByClientIdWishlistFound() {
        String clientId = UUID.randomUUID().toString();

        var product = Product.builder()
                .id(UUID.randomUUID().toString())
                .code("10")
                .description("TV LED LG 501 4K")
                .price(BigDecimal.valueOf(2800))
                .build();

        var wishlistReturnedByDB = Wishlist.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .products(Collections.singletonList(product))
                .build();

        Mockito.when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlistReturnedByDB));

        var wishlist = wishlistService.executeFindByClientId(clientId);

        Assertions.assertEquals(wishlist, wishlistReturnedByDB);
    }

    @Test
    void executeFindByClientIdWishlistNotFound() {
        String clientId = UUID.randomUUID().toString();
        Mockito.when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.empty());
        Assertions.assertThrows(WishlistNotFoundException.class, () -> wishlistService.executeFindByClientId(clientId));
    }

}



















