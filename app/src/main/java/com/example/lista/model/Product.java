package com.example.lista.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String name;
    private Float valor;

    public Product(int id, String name, Float valor) {
        this.id = id;
        this.name = name;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    @NonNull
    @Override
    public String toString() {
        return name;

    }
}