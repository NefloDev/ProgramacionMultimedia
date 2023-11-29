package com.example.recyclerviewpokemon.entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class PokemonsViewModel extends AndroidViewModel {
    private PokemonsRepo pokemonsRepo;
    MutableLiveData<ArrayList<Pokemon>> listPokemonMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Pokemon> selectedPokemon = new MutableLiveData<>();
    public PokemonsViewModel(@NonNull Application application) {
        super(application);
        pokemonsRepo = new PokemonsRepo();
        listPokemonMutableLiveData.setValue(pokemonsRepo.obtain());
    }
    public MutableLiveData<ArrayList<Pokemon>> obtain(){
        return listPokemonMutableLiveData;
    }
    public void select(Pokemon element){
        selectedPokemon.setValue(element);
    }
    public MutableLiveData<Pokemon> selected(){
        return selectedPokemon;
    }
    public void insert(Pokemon element){
        pokemonsRepo.insert(element, elmts -> listPokemonMutableLiveData.setValue(elmts));
    }
    public void delete(Pokemon element){
        pokemonsRepo.delete(element, elmts -> listPokemonMutableLiveData.setValue(elmts));
    }
}
