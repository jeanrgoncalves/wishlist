package com.wishlist.application.client;

import com.wishlist.domain.client.Client;
import com.wishlist.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @GetMapping
    @Operation(summary = "Lista todos os Clientes.")
    public ResponseEntity<List<Client>> findAll() {
        var clients = service.findAll();
        return ResponseEntity.ok(clients);
    }

}
