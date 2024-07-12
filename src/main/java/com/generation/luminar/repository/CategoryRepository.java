package com.generation.luminar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.luminar.model.Category;

public interface CategoryRepository extends JpaRepository<Category ,Long> {
    public List<Category>findAllByNameContainsIgnoreCase(@Param("name")String name);
}