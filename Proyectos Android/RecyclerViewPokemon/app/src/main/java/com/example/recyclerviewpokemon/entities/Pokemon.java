package com.example.recyclerviewpokemon.entities;

import android.net.Uri;

import java.util.List;

public class Pokemon {
    public int code;
    public String name;
    public String types;
    public String species;
    public double height;
    public double weight;
    public Uri image;

    public Pokemon(int code, String name, String types, String species, double height, double weight, Uri image) {
        this.code = code;
        this.name = name;
        this.types = types;
        this.species = species;
        this.height = height;
        this.weight = weight;
        this.image = image;
    }
}
