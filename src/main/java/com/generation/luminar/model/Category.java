package com.generation.luminar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O Atributo Nome é obrigatório!")
    @Size(max = 255, message = "O atributo nome deve conter " +
            "no máximo 255 caracteres")
    private String name;


    @Size(max = 255, message = "O atributo título deve conter " +
            "no máximo 255 caracteres")
    private String description;

}