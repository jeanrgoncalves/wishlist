package com.wishlist.config;

import com.wishlist.domain.client.Client;
import com.wishlist.domain.client.ClientRepository;
import com.wishlist.domain.product.Product;
import com.wishlist.domain.product.ProductRepository;
import com.wishlist.domain.wishlist.Wishlist;
import com.wishlist.domain.wishlist.WishlistRepository;
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
    private final WishlistRepository wishlistRepository;

    @Autowired
    public DataLoader(ClientRepository clientRepository, ProductRepository productRepository, WishlistRepository wishlistRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.wishlistRepository = wishlistRepository;
        loadData();
    }

    private void loadData() {
        if (clientRepository.findAll().isEmpty()) {
            var clients = List.of(
                    Client.of("Gustavo Gómez"),
                    Client.of("Raphael Veiga"),
                    Client.of("Marcos Rocha"),
                    Client.of("Abel Ferreira"));
            clientRepository.saveAll(clients);
            log.info("Realizada carga inicial de 4 Clientes");
        }

        if (productRepository.findAll().isEmpty()) {
            var products = List.of(
                    Product.of("1", "TV LG 50' 4K", BigDecimal.valueOf(2800)),
                    Product.of("2", "Ar Condicionado Philco Inverter 12000 btus", BigDecimal.valueOf(1900)),
                    Product.of("3", "Notebook Acer", BigDecimal.valueOf(4000)),
                    Product.of("4", "Teclado sem fio Logitech", BigDecimal.valueOf(80)),
                    Product.of("5", "Mouse sem fio Logitech", BigDecimal.valueOf(50)),
                    Product.of("6", "Monitor AOC 19'", BigDecimal.valueOf(1300)),
                    Product.of("7", "Ferro Back&Decker", BigDecimal.valueOf(120)),
                    Product.of("8", "Fone de ouvido sem fio Edifier", BigDecimal.valueOf(250)),
                    Product.of("9", "Aspirador de pó Philco 2000w", BigDecimal.valueOf(480)),
                    Product.of("10", "Fogão Dako Magister", BigDecimal.valueOf(1000)),
                    Product.of("11", "Geladeira Bastemp 480L", BigDecimal.valueOf(3900)),
                    Product.of("12", "Microondas LG", BigDecimal.valueOf(725)),
                    Product.of("13", "Máquina de Lavar Panasonic", BigDecimal.valueOf(1800)),
                    Product.of("14", "Smartphone Redmi Note 10", BigDecimal.valueOf(2000)),
                    Product.of("15", "Tablet Samsung", BigDecimal.valueOf(1750)),
                    Product.of("16", "Air Fryer Phillips", BigDecimal.valueOf(1900)),
                    Product.of("17", "Jogo de Panelas Rochedo", BigDecimal.valueOf(250)),
                    Product.of("18", "Jogo de Talheres Tramontina", BigDecimal.valueOf(98)),
                    Product.of("19", "Jogo de Cama Santista", BigDecimal.valueOf(195)),
                    Product.of("20", "Cama Box Queen Castor ", BigDecimal.valueOf(1800)),
                    Product.of("21", "Multiprocessador Phillips", BigDecimal.valueOf(520))
            );
            productRepository.saveAll(products);
            log.info("Realizada carga inicial de 21 Produtos");
        }

        if (wishlistRepository.findAll().isEmpty()) {
            var allClients = clientRepository.findAll();
            if (!allClients.isEmpty()) {
                var client = allClients.get(0);

                var allProducts = productRepository.findAll();
                if (!allProducts.isEmpty()) {
                    allProducts.remove(0);

                    var wishlist = Wishlist.builder().clientId(client.getId()).build();
                    for (var product : allProducts)
                        wishlist.addProduct(product);

                    wishlistRepository.save(wishlist);
                    log.info("Realizada carga inicial de uma Wishlist contendo 20 Produtos");
                }
            }


        }

    }

}
