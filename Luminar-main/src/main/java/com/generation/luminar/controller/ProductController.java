package com.generation.luminar.controller;

import com.generation.luminar.model.Product;
import com.generation.luminar.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Product>> getByProduct(@PathVariable String name) {
        return ResponseEntity.ok(productRepository.findAllByNameContainsIgnoreCase(name));
    }

    @PostMapping
    public ResponseEntity<Product> post(@Valid @RequestBody Product name) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(name));
    }

    @PutMapping
    public ResponseEntity<Product> put(@Valid @RequestBody Product name) {
        return productRepository.findById(name.getId())
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(productRepository.save(name)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Product> name = productRepository.findById(id);

        if (name.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        productRepository.deleteById(id);
    }

}
