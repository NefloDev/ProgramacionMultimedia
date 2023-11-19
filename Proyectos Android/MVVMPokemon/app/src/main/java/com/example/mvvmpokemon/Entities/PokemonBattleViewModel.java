package com.example.mvvmpokemon.Entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class PokemonBattleViewModel extends AndroidViewModel {
    protected PokemonBattle battle;
    protected PokemonCreateViewModel vm;
    public MutableLiveData<String> damage1 = new MutableLiveData<>();
    public MutableLiveData<String> damage2 = new MutableLiveData<>();
    public MutableLiveData<String> winner = new MutableLiveData<>();
    public PokemonBattleViewModel(@NonNull Application application) {
        super(application);
        battle = new PokemonBattle();
        vm = PokemonCreateViewModel.getInstance(application);
    }

    public void startBattle(PokemonCreate.Pokemon pokemon1, PokemonCreate.Pokemon pokemon2){
        battle.startBattle(pokemon1, pokemon2, new PokemonBattle.BattleListener() {
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
}
