package com.generation.luminar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@NotBlank(message = "O Atributo Nome é obrigatório!")
    @Size(max = 255, message = "O atributo nome deve conter " +
            "no máximo 255 caracteres")
    private String name;


    @Size(max = 255, message = "O atributo título deve conter " +
            "no máximo 255 caracteres")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("category")
    private List<Product> products;
}