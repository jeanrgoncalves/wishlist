package com.wishlist.service.product;

import com.wishlist.domain.product.Product;
import com.wishlist.domain.product.ProductRepository;
import com.wishlist.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Product findProductById(String id) {
        log.info("Verificando se existe produto com o id {}", id);

        var product = repository.findById(id).orElseGet(() -> {
            log.error("Produto não encontrado com o id {}", id);
            throw new ProductNotFoundException();
        });

        log.info("Encontrado produto: {}", product);
        return product;
    }

}
