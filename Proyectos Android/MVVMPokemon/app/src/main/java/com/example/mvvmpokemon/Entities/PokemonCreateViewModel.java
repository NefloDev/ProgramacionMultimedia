package com.example.mvvmpokemon.Entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmpokemon.MainActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PokemonCreateViewModel extends AndroidViewModel {
    public static PokemonCreateViewModel instance;
    protected Executor executor;
    protected PokemonCreate pok;
    public MutableLiveData<String> errorName;
    public MutableLiveData<String> errorHp;
    public MutableLiveData<String> errorAtk;
    public MutableLiveData<String> errorDef;
    public MutableLiveData<String> errorSpAtk;
    public MutableLiveData<String> errorSpDef;
    public MutableLiveData<Boolean> creating;
    public MutableLiveData<PokemonCreate.Pokemon> pokemon1;
    public MutableLiveData<PokemonCreate.Pokemon> pokemon2 ;
    public PokemonCreateViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        pok = new PokemonCreate();
    }

    public void initErrors(){
        errorName = new MutableLiveData<>();
        errorHp = new MutableLiveData<>();
        errorAtk = new MutableLiveData<>();
        errorDef = new MutableLiveData<>();
        errorSpAtk = new MutableLiveData<>();
        errorSpDef = new MutableLiveData<>();
        creating = new MutableLiveData<>();
    }

    public PokemonCreate.Pokemon create(String name, int hp, int atk, int def, int spAtk, int spDef){
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

        return pokemon;
    }

    public MutableLiveData<PokemonCreate.Pokemon> getPokemon1(){
        return pokemon1;
    }
    public MutableLiveData<PokemonCreate.Pokemon> getPokemon2(){
        return pokemon2;
    }

    public void setPokemon1(String name, int hp, int atk, int def, int spAtk, int spDef){
        pokemon1 = new MutableLiveData<>();
        pokemon1.postValue(create(name, hp, atk, def, spAtk, spDef));
    }

    public void setPokemon2(String name, int hp, int atk, int def, int spAtk, int spDef){
        pokemon2 = new MutableLiveData<>();
        pokemon2.postValue(create(name, hp, atk, def, spAtk, spDef));
    }

    public static synchronized PokemonCreateViewModel getInstance(Application application){
        if(instance == null){
            instance = new PokemonCreateViewModel(application);
        }
        return instance;
    }
}
