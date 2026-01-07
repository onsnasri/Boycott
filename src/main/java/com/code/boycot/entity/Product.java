package com.code.boycot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean boycott;
    private String alternatives;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBoycott() {
        return boycott;
    }

    public String getAlternatives() {
        return alternatives;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoycott(boolean boycott) {
        this.boycott = boycott;
    }

    public void setAlternatives(String alternatives) {
        this.alternatives = alternatives;
    }
}
