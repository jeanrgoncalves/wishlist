package com.wishlist.application.wishlist;

import com.wishlist.service.wishlist.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/wishlists/products")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService service;

    @PostMapping
    @Operation(summary = "Adiciona um produto na Wishlist do cliente informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto adicionado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou tamanho máximo (20) da Wishlist atingido.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto ou cliente não encontrado.", content = @Content)
    })
    public ResponseEntity<WishlistDto> addProduct(@RequestBody WishlistRequest request) {
        var wishlist =  service.addProduct(request.getProductId(), request.getClientId());
        var dto = WishlistDto.from(wishlist);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/wishlists/products/clients/")
                .path(request.getClientId())
                .build().toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping
    @Operation(summary = "Remove um produto da Wishlist do cliente informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto removido com sucesso ou produto não encontrado na Wishlist."),
            @ApiResponse(responseCode = "404", description = "Wishlist não encontrada.")
    })
    public ResponseEntity<Void> removeProduct(@RequestBody WishlistRequest request) {
        service.removeProduct(request.getProductId(), request.getClientId());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/clients/{clientId}")
    @Operation(summary = "Lista todos os produtos na Wishlist do cliente informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Wishlist não encontrada.", content = @Content)
    })
    public ResponseEntity<WishlistDto> getProducts(@PathVariable String clientId) {
        var wishlist =  service.findByClientId(clientId);
        var dto = WishlistDto.from(wishlist);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{productId}/clients/{clientId}/exists")
    @Operation(summary = "Verifica se um determinado produto existe na Wishlist do cliente informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação concluída com sucesso."),
            @ApiResponse(responseCode = "404", description = "Wishlist não encontrada.", content = @Content)
    })
    public ResponseEntity<Boolean> checkProductExists(@PathVariable String productId, @PathVariable String clientId) {
        return ResponseEntity.ok(service.checkProductExists(productId, clientId));
    }

    @GetMapping
    @Operation(summary = "Lista todas as Wishlists.")
    public ResponseEntity<List<WishlistDto>> findAll() {
        var wishlists = service.findAll();
        var wishlistDtos = wishlists.stream()
                .map(WishlistDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(wishlistDtos);
    }

}
