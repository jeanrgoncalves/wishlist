package com.wishlist.service.product;

import com.wishlist.domain.product.Product;

import java.util.List;

public interface ProductService {

    Product findProductById(String id);

    List<Product> findAll();

}
