package com.wishlist.application;

import com.wishlist.domain.product.Product;
import com.wishlist.domain.product.ProductRepository;
import com.wishlist.exception.WishlistMaxSizeException;
import com.wishlist.service.wishlist.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController("/api/v1/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final ProductRepository productRepository;
    private final WishlistService service;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Test erro")
    })
    public Product teste() {
        /*var product = productRepository.findById("64454e3bc0e188e8df242d90").get();
        return product;*/

        throw new WishlistMaxSizeException();
    }

    @PostMapping(path = "/products")
    @Operation(summary = "Adiciona um produto na Wishlist no cliente informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto adicionado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou tamanho máximo (20) da Wishlist atingido.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto ou cliente não encontrado.", content = @Content),
    })
    public ResponseEntity<WishlistDto> addProduct(@RequestBody WishlistAddProductRequest request) {
        var wishlist =  service.addProduct(request.getProductId(), request.getClientId());
        var dto = WishlistDto.from(wishlist);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/wishlists/clients/")
                .path(request.getClientId())
                .build().toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
