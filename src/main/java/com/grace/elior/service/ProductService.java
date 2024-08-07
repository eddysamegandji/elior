package com.grace.elior.service;

import com.grace.api.model.ProductDto;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> createProduct(ProductDto productDto);
    Optional<ProductDto> updateProduct(ProductDto productDto);
    Optional<ProductDto> getProduct(Long id);
    List<ProductDto> getProducts();
    void deleteProduct(Long id);
}
