package com.wishlist.service.client;

import com.wishlist.domain.client.ClientRepository;
import com.wishlist.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClientServiceImpl implements ClientService{
    private final ClientRepository repository;

    @Override
    public boolean existClientById(String id) {
        log.info("Verificando se existe cliente com o id {}", id);

        var client = repository.findById(id).orElseGet(() -> {
            log.error("Cliente não encontrado com o id {}", id);
            throw new ClientNotFoundException();
        });

        log.info("Encontrado cliente: {}", client);
        return true;
    }

}
