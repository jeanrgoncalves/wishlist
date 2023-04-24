package com.wishlist.service.wishlist;

import com.wishlist.domain.wishlist.Wishlist;
import com.wishlist.domain.wishlist.WishlistRepository;
import com.wishlist.service.client.ClientService;
import com.wishlist.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    private final ClientService clientService;
    private final ProductService productService;
    private final WishlistRepository repository;

    @Override
    public Wishlist addProduct(String productId, String clientId) {
        log.info("Iniciando processo para adicionar produto com id {} à Wishlist do cliente com id {}", productId, clientId);

        var wishlist = createOrFindWishlistByClientId(clientId);
        var product = productService.findProductById(productId);
        wishlist.addProduct(product);
        repository.save(wishlist);

        log.info("Produto com id {} adicionado com sucesso à Wishlist do cliente com id {}", productId, clientId);
        return wishlist;
    }

    private Wishlist createOrFindWishlistByClientId(String clientId) {
        clientService.existClientById(clientId);
        var optWishlist = repository.findByClientId(clientId);
        return optWishlist.orElseGet(() -> Wishlist.builder()
                .clientId(clientId)
                .build());
    }
}
