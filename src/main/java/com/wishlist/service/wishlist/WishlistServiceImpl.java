package com.wishlist.service.wishlist;

import com.wishlist.domain.wishlist.Wishlist;
import com.wishlist.domain.wishlist.WishlistRepository;
import com.wishlist.exception.WishlistNotFoundException;
import com.wishlist.service.client.ClientService;
import com.wishlist.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Wishlist createOrFindWishlistByClientId(String clientId) {
        clientService.existClientById(clientId);
        var optWishlist = repository.findByClientId(clientId);
        return optWishlist.orElseGet(() -> Wishlist.builder()
                .clientId(clientId)
                .build());
    }

    @Override
    public void removeProduct(String productId, String clientId) {
        log.info("Iniciando processo para remover produto com id {} da Wishlist do cliente com id {}", productId, clientId);

        var wishlist = repository.findByClientId(clientId).orElseGet(() -> {
            log.error("Não foi encontrada Wishlist para o cliente com id {}", clientId);
            throw new WishlistNotFoundException();
        });

        wishlist.removeProduct(productId);
        if (wishlist.getProducts().size() == 0) {
            repository.deleteById(wishlist.getId());
            log.info("A Wishlist do cliente com id {} foi deletada após remover seu último produto", clientId);
        } else {
            repository.save(wishlist);
            log.info("Wishlist do cliente com id {} salva com sucesso", clientId);
        }
    }

    @Override
    public Wishlist findByClientId(String clientId) {
        log.info("Iniciando processo para listar os produtos da Wishlist do cliente com id {}", clientId);

        var wishlist = executeFindByClientId(clientId);

        log.info("Wishlist do cliente com id {} encontrada, devolvendo a lista de produtos", clientId);
        return wishlist;
    }

    @Override
    public Boolean checkProductExists(String productId, String clientId) {
        log.info("Iniciando processo para verificar se o produto com id {} existe na Wishlist do cliente com id {}", productId, clientId);
        var wishlist = executeFindByClientId(clientId);
        return wishlist.checkProductExists(productId);
    }

    public Wishlist executeFindByClientId(String clientId) {
        return repository.findByClientId(clientId).orElseGet(() -> {
            log.error("Wishlist não encontrada para o cliente com id {}", clientId);
            throw new WishlistNotFoundException();
        });
    }

    public List<Wishlist> findAll() {
        return repository.findAll();
    }

}
