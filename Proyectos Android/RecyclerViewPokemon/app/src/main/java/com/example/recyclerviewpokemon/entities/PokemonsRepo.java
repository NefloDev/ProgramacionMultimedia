package com.example.recyclerviewpokemon.entities;

import android.net.Uri;

import com.example.recyclerviewpokemon.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PokemonsRepo {
    private static final String URIPARSE = "android.resource://com.example.recyclerviewpokemon/";
    ArrayList<Pokemon> pokemons;
    public interface Callback{
        void whenFinished(ArrayList<Pokemon> pokemons);
    }
    public PokemonsRepo(){
        pokemons = new ArrayList<>(Arrays.asList(
                new Pokemon(1, "Bulbasaur", "Grass, Poison", "Seed Pokémon", 0.7, 6.9,
                        Uri.parse(URIPARSE + R.drawable.bulbasaur)),
                new Pokemon(4, "Charmander", "Fire", "Lizard Pokémon", 0.6, 8.5,
                        Uri.parse(URIPARSE + R.drawable.charmander)),
                new Pokemon(7, "Squirtle", "Water", "Tiny Turtle Pokémon", 0.5, 9.0,
                        Uri.parse(URIPARSE + R.drawable.squirtle)),
                new Pokemon(10, "Caterpie", "Bug", "Worm Pokémon", 0.3, 2.9,
                        Uri.parse(URIPARSE + R.drawable.caterpie)),
                new Pokemon(13, "Weedle", "Bug, Posion", "Hairy Bug Pokémon", 0.3, 3.2,
                        Uri.parse(URIPARSE + R.drawable.weedle)),
                new Pokemon(16, "Pidgey", "Normal, Flying", "Tiny Bird Pokémon", 0.3, 1.8,
                        Uri.parse(URIPARSE + R.drawable.pidgey))
        ));
    }
    public ArrayList<Pokemon> obtain(){return pokemons;}
    public void insert(Pokemon pokemon, Callback callback){
        pokemons.add(pokemon);
        callback.whenFinished(pokemons);
    }
    public void delete(Pokemon pokemon, Callback callback){
        pokemons.remove(pokemon);
        callback.whenFinished(pokemons);
    }
}
