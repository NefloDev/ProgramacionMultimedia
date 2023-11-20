package com.example.mvvmpokemon.Entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PokemonViewModel extends AndroidViewModel {
    public static PokemonViewModel instance;
    protected Executor executor;
    protected PokemonModel pok;
    public MutableLiveData<String> errorName;
    public MutableLiveData<String> errorHp;
    public MutableLiveData<String> errorAtk;
    public MutableLiveData<String> errorDef;
    public MutableLiveData<String> errorSpAtk;
    public MutableLiveData<String> errorSpDef;
    public MutableLiveData<Boolean> creating;
    public MutableLiveData<PokemonModel.Pokemon> pokemon1;
    public MutableLiveData<PokemonModel.Pokemon> pokemon2;
    public MutableLiveData<String> damage1 = new MutableLiveData<>();
    public MutableLiveData<String> damage2 = new MutableLiveData<>();
    public MutableLiveData<String> winner = new MutableLiveData<>();
    public PokemonViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        pok = new PokemonModel();
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

    public PokemonModel.Pokemon create(String name, int hp, int atk, int def, int spAtk, int spDef){
        final PokemonModel.Pokemon pokemon = new PokemonModel.Pokemon(name, hp, atk, def, spAtk, spDef);

        executor.execute(() -> pok.create(pokemon, new PokemonModel.Callback() {

            @Override
            public void whenCreatingPokemon(PokemonModel.Pokemon pokemon) {
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

    public void startBattle(PokemonModel.Pokemon pokemon1, PokemonModel.Pokemon pokemon2){
        pok.startBattle(pokemon1, pokemon2, new PokemonModel.BattleListener() {
            @Override
            public void onTurn1(int dmg) {
                damage1.postValue(String.valueOf(dmg));
            }

            @Override
            public void onTurn2(int dmg) {
                damage2.postValue(String.valueOf(dmg));
            }

            @Override
            public void onBattleEnd(String result) {
                winner.postValue(result);
            }
        });
    }

    public MutableLiveData<PokemonModel.Pokemon> getPokemon1(){
        return pokemon1;
    }
    public MutableLiveData<PokemonModel.Pokemon> getPokemon2(){
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

    public boolean getError(){
        return errorName.getValue()!=null ||
                errorSpDef.getValue()!=null ||
                errorAtk.getValue()!=null ||
                errorSpAtk.getValue()!=null ||
                errorDef.getValue()!=null ||
                errorHp.getValue()!=null;
    }

    public static synchronized PokemonViewModel getInstance(Application application){
        if(instance == null){
            instance = new PokemonViewModel(application);
        }
        return instance;
    }
}
