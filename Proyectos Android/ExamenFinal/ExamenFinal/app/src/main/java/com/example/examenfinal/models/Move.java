package com.example.examenfinal.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private String accuracy;
    private String name;

    // TODO falta serializar el listado de Pokémon, en el JSON de la API se llama learned_by_pokemon no pokemons.
    @SerializedName("learned_by_pokemon")
    private List<PokemonListItem> pokemons;

    public String getAccuracy() {
        return accuracy;
    }

    public String getName() {
        return name;
    }

    public List<PokemonListItem> getPokemons() {
        return pokemons;
    }

    /**
     *  Junta todo el listado de nombres de Pokémon en una cadena y la devuelve.
     *  Actualmente NO funciona porque hay que serializar pokemons.
     *
     * @return      Una cadena con el nombre de todos los Pokémon que aprenden este movimiento.
     */
    public String getPokemonsString() {
        String s = "";
        for (int i = 0; pokemons != null && i<pokemons.size(); i++) {
            s += pokemons.get(i).getName() + " ";
        }
        return  s;
    }

}
