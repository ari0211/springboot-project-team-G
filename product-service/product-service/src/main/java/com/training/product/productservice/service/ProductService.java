package com.training.product.productservice.service;

import com.training.product.productservice.entity.Product;
import com.training.product.productservice.model.ProductDto;
import com.training.product.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto saveProduct(ProductDto dto) {
        Product product = convertDtoToEntity(dto);
        Product saved = productRepository.save(product);
        return convertEntityToDto(saved);
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertEntityToDto(product);
    }

    // ✅ Get all products
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    // -------------- Converters ------------------

    private Product convertDtoToEntity(ProductDto dto) {
        return new Product(
                dto.getId(),
                dto.getSku(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getCategory()
        );
    }

    private ProductDto convertEntityToDto(Product entity) {
        return new ProductDto(
                entity.getId(),
                entity.getSku(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getCategory()
        );
    }
}
