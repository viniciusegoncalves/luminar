package com.generation.luminar.repository;

import com.generation.luminar.model.Category;
import com.generation.luminar.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findAllByNameContainsIgnoreCase(@Param("name") String name);

}
