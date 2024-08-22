package com.generation.luminar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tb_product")
@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "O atributo nome é obrigatório")
    @Size(max = 255, message = "O atributo nome deve conter no máximo 255 caracteres")
    private String name;

    @NotBlank(message = "O atributo descrição é obrigatório")
    private String description;

    @NotNull
    @PositiveOrZero(message = "A quantidade deve ser zero ou positivo")
    private int quantity;

    @NotNull
    @PositiveOrZero(message = "O preço deve ser zero ou positivo")
    private Double price;


    private String image;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    @ManyToOne
    private User user;
    

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


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
