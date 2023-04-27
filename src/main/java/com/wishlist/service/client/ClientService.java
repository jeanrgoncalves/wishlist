package com.wishlist.service.client;

import com.wishlist.domain.client.Client;

import java.util.List;

public interface ClientService {
    boolean existClientById(String id);

    List<Client> findAll();

}
