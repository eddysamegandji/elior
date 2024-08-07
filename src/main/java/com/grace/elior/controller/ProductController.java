package com.grace.elior.controller;

import com.grace.api.handler.ProductResourceApi;
import com.grace.api.model.ProductDto;
import com.grace.elior.service.ProductService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController implements ProductResourceApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto) {
        if(productDto.getId() != null || productDto.getName().isBlank() || productDto.getUnitPurchasePrice().isNaN()) {
            return badRequest().build();
        }
        Optional<ProductDto> createdProduct = productService.createProduct(productDto);
        return createdProduct
                .map(a -> ResponseEntity.status(HttpStatus.CREATED).body(a))
                .orElseGet(() -> internalServerError().build());
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getProducts();
        return products.isEmpty() ? noContent().build() : ok(products);
    }

    @Override
    public ResponseEntity<ProductDto> updateProduct(Long id, ProductDto productDto) {
        if (id == null || productDto.getId() == null || !id.equals(productDto.getId())) {
            return badRequest().build();
        }
        Optional<ProductDto> updatedProduct = productService.updateProduct(productDto);
        return of(updatedProduct);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        try {
            productService.deleteProduct(id);
            return noContent().build();
        } catch (EmptyResultDataAccessException e) {
            log.error("Product not found while deleting");
            return notFound().build();
        } catch (Exception e) {
            log.error("Error while deleting product");
            return internalServerError().build();
        }
    }
}
