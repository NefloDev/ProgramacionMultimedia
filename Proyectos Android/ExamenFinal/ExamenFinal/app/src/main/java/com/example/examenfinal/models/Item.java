package com.example.examenfinal.models;

import java.util.List;

public class Item {

    private String name;
    private Category category;
    private long cost;
    private List<ItemEffect> effect_entries;
    private Sprite sprites;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category.getName();
    }

    public long getCost() {
        return cost;
    }

    public List<ItemEffect> getEffect_entries() {
        return effect_entries;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public String getItemEffects(){
        StringBuilder sb = new StringBuilder();
        for (ItemEffect eff: effect_entries) {
            sb.append(eff.getEffect()).append(" ");
        }
        return sb.toString();
    }
}
