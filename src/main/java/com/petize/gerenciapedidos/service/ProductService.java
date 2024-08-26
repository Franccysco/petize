package com.petize.gerenciapedidos.service;

import com.petize.gerenciapedidos.controller.dto.ProductDTO;
import com.petize.gerenciapedidos.model.Product;
import com.petize.gerenciapedidos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductDTO productDto){
        Product product = new Product(null, productDto.name(),productDto.description(), productDto.price());
        return this.productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO productDto){
        Product product = this.productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        return this.productRepository.save(product);
    }

    public void deleteProduct(Long id){
        this.productRepository.deleteById(id);
    }

    public List<Product> listAllProducts(){
        return this.productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return this.productRepository.findById(id);
    }

}
