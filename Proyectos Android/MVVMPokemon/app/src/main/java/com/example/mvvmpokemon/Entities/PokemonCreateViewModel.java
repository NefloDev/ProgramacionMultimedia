package com.example.mvvmpokemon.Entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PokemonCreateViewModel extends AndroidViewModel {
    protected Executor executor;
    protected PokemonCreate pok;
    public MutableLiveData<String> errorName = new MutableLiveData<>();
    public MutableLiveData<String> errorHp = new MutableLiveData<>();
    public MutableLiveData<String> errorAtk = new MutableLiveData<>();
    public MutableLiveData<String> errorDef = new MutableLiveData<>();
    public MutableLiveData<String> errorSpAtk = new MutableLiveData<>();
    public MutableLiveData<String> errorSpDef = new MutableLiveData<>();
    public MutableLiveData<PokemonCreate.Pokemon> pokemonLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> creating = new MutableLiveData<>();
    public PokemonCreateViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        pok = new PokemonCreate();
    }

    public void create(String name, int hp, int atk, int def, int spAtk, int spDef){
        final PokemonCreate.Pokemon pokemon = new PokemonCreate.Pokemon(name, hp, atk, def, spAtk, spDef);

        executor.execute(() -> pok.create(pokemon, new PokemonCreate.Callback() {

            @Override
            public void whenCreatingPokemon(PokemonCreate.Pokemon pokemon) {
                errorName = null;
                errorHp = null;
                errorAtk = null;
                errorDef = null;
                errorSpAtk = null;
                errorSpDef = null;
                pokemonLiveData.postValue(pokemon);
            }

            @Override
            public void whenErrorNameNotAlphanumeric(String error) {
                errorName.postValue(error);
            }

            @Override
            public void whenErrorHpHigherThanMaxOrLowerThanMin(int minVal, int maxVal) {
                errorHp.postValue("Hp must be between " + minVal + "-" + maxVal);
            }

            @Override
            public void whenErrorAttackHigherThanMaxOrLowerThanMin(int minVal, int maxVal) {
                errorAtk.postValue("Attack must be between " + minVal + "-" + maxVal);
            }

            @Override
            public void whenErrorDefenseHigherThanMaxOrLowerThanMin(int minVal, int maxVal) {
                errorDef.postValue("Defense must be between " + minVal + "-" + maxVal);
            }

            @Override
            public void whenErrorSpAttackHigherThanMaxOrLowerThanMin(int minVal, int maxVal) {
                errorSpAtk.postValue("SpAttack must be between " + minVal + "-" + maxVal);
            }

            @Override
            public void whenErrorSpDefHigherThanMaxOrLowerThanMin(int minVal, int maxVal) {
                errorSpDef.postValue("SpDef must be between " + minVal + "-" + maxVal);
            }

            @Override
            public void whenStartCreating() {
                creating.postValue(true);
            }

            @Override
            public void whenStopCreating() {
                creating.postValue(false);
            }
        }));
    }

    public MutableLiveData<PokemonCreate.Pokemon> getPokemon(){
        return pokemonLiveData;
    }
}
