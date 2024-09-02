package com.grace.elior.service.impl;

import com.grace.api.model.ProductDto;
import com.grace.elior.mapper.ProductMapper;
import com.grace.elior.model.Product;
import com.grace.elior.repository.ProductRepository;
import com.grace.elior.service.ProductService;
import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Optional<ProductDto> createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product = productRepository.save(product);
        return Optional.ofNullable(productMapper.toDto(product));
    }

    @Override
    @Transactional
    public Optional<ProductDto> updateProduct(ProductDto productDto) {
        Optional<Product> product = productRepository.findById(productDto.getId());
        if (product.isPresent()) {
            Product updatedProduct = productMapper.toEntity(productDto);
            updatedProduct = productRepository.save(updatedProduct);
            return Optional.ofNullable(productMapper.toDto(updatedProduct));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductDto> getProduct(Long id) {
        return productRepository.findById(id).map(productMapper::toDto);
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().sorted(Comparator.comparing(Product::getName)).map(productMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Product not found", 1);
        }
    }
}
