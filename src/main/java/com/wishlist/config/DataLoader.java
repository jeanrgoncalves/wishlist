package com.wishlist.config;

import com.wishlist.domain.client.Client;
import com.wishlist.domain.client.ClientRepository;
import com.wishlist.domain.product.Product;
import com.wishlist.domain.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class DataLoader {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DataLoader(ClientRepository clientRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        loadData();
    }

    private void loadData() {
        if (clientRepository.findAll().isEmpty()) {
            var client1 = Client.of("Gustavo Gómez");
            var client2 = Client.of("Raphael Veiga");
            clientRepository.saveAll(List.of(client1, client2));
            log.info("Realizada carga inicial de 2 Clientes");
        }

        if (productRepository.findAll().isEmpty()) {
            var product1 = Product.of("10", "TV LG 50' 4K", BigDecimal.valueOf(2800));
            var product2 = Product.of("20", "Ar Condicionado Philco Inverter 12000 btus", BigDecimal.valueOf(1900));
            productRepository.saveAll(List.of(product1, product2));
            log.info("Realizada carga inicial de 2 Produtos");
        }
    }

}
