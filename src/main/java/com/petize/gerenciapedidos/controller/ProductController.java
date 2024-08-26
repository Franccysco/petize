package com.petize.gerenciapedidos.controller;

import com.petize.gerenciapedidos.controller.dto.ProductDTO;
import com.petize.gerenciapedidos.model.Product;
import com.petize.gerenciapedidos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllproducts(){
        return ResponseEntity.ok(this.productService.listAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return this.productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDto){
        return ResponseEntity.ok(this.productService.createProduct(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDto){
        return ResponseEntity.ok(this.productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
