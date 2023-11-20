package com.example.mvvmpokemon.Entities;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PokemonModel {
    public static class Pokemon {
        public String name;
        public int hp,atk, def, spAtk, spDef;
        public Pokemon(String name, int hp, int atk, int def, int spAtk, int spDef){
            this.name = name;
            this.hp = hp;
            this.atk = atk;
            this.def = def;
            this.spAtk = spAtk;
            this.spDef = spDef;
        }
    }

    interface Callback{
        void whenCreatingPokemon(Pokemon pokemon);
        void whenErrorNameNotAlphanumeric(String error);
        void whenErrorHpHigherThanMaxOrLowerThanMin(int minVal, int maxVal);
        void whenErrorAttackHigherThanMaxOrLowerThanMin(int minVal, int maxVal);
        void whenErrorDefenseHigherThanMaxOrLowerThanMin(int minVal, int maxVal);
        void whenErrorSpAttackHigherThanMaxOrLowerThanMin(int minVal, int maxVal);
        void whenErrorSpDefHigherThanMaxOrLowerThanMin(int minVal, int maxVal);
        void whenStartCreating();
        void whenStopCreating();
    }

    interface BattleListener{
        void onTurn1(int action);
        void onTurn2(int action);
        void onBattleEnd(String result);
    }
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> fighting;

    public void create(Pokemon pokemon, Callback callback){
        callback.whenStartCreating();
        int minValue = 0;
        int maxValue = 999;
        boolean error = false;
        String alphanumericRegEx = "[0-9A-Za-z]";

        if(pokemon.hp < minValue || pokemon.hp > maxValue){
            callback.whenErrorHpHigherThanMaxOrLowerThanMin(minValue, maxValue);
            error = true;
        }
        if(pokemon.atk < minValue || pokemon.atk > maxValue){
            callback.whenErrorAttackHigherThanMaxOrLowerThanMin(minValue, maxValue);
            error = true;
        }
        if(pokemon.def < minValue || pokemon.def > maxValue){
            callback.whenErrorDefenseHigherThanMaxOrLowerThanMin(minValue, maxValue);
            error = true;
        }
        if(pokemon.spAtk < minValue || pokemon.spAtk > maxValue){
            callback.whenErrorSpAttackHigherThanMaxOrLowerThanMin(minValue, maxValue);
            error = true;
        }
        if(pokemon.spDef < minValue || pokemon.spDef > maxValue){
            callback.whenErrorSpDefHigherThanMaxOrLowerThanMin(minValue, maxValue);
            error = true;
        }
        if(!pokemon.name.matches(alphanumericRegEx)){
            callback.whenErrorNameNotAlphanumeric("Must be alphanumeric");
            error = true;
        }

        if(!error){
            try{
                Thread.sleep(2500);
            }catch (InterruptedException ignored){}
            callback.whenCreatingPokemon(pokemon);
        }

        callback.whenStopCreating();
    }

    public void startBattle(PokemonModel.Pokemon pokemon1, PokemonModel.Pokemon pokemon2, BattleListener listener) {
        if(fighting == null || fighting.isCancelled()){
            fighting = scheduler.scheduleAtFixedRate(new Runnable() {
                int turnCounter = 0;
                boolean isBattleActive = true;
                @Override
                public void run() {
                    if (!isBattleActive) {
                        fighting.cancel(true);
                        return;
                    }

                    if (turnCounter % 2 == 0) {
                        performTurn1(pokemon1, pokemon2, listener);
                    } else {
                        performTurn2(pokemon2, pokemon1, listener);
                    }

                    turnCounter++;

                    // Verificación de fin de batalla
                    if (pokemon1.hp <= 0 || pokemon2.hp <= 0) {
                        isBattleActive = false;
                        String result = (pokemon1.hp <= 0) ? pokemon2.name + " wins!" : pokemon1.name + " wins!";
                        listener.onBattleEnd(result);
                    }
                }
            }, 0, 2, TimeUnit.SECONDS);
        }
    }

    private void performTurn1(PokemonModel.Pokemon attacker, PokemonModel.Pokemon defender, BattleListener listener) {
        Random r = new Random();
        int damage;
        boolean special = false;
        if(r.nextInt(2) == 0){ //Normal Attack
            damage = (attacker.atk - defender.def);
        }else{//Special Attack
            damage = (attacker.spAtk - defender.spDef);
            special = true;
        }
        damage = damage<0?0:damage;
        defender.hp -= damage;
        listener.onTurn1(special ? damage*1000 : damage);
    }

    private void performTurn2(PokemonModel.Pokemon attacker, PokemonModel.Pokemon defender, BattleListener listener) {
        Random r = new Random();
        int damage;
        boolean special = false;
        if(r.nextInt(2) == 0){ //Normal Attack
            damage = (attacker.atk - defender.def);
        }else{//Special Attack
            damage = (attacker.spAtk - defender.spDef);
            special = true;
        }
        damage = damage<0?0:damage;
        defender.hp -= damage;
        listener.onTurn2(special ? damage*1000 : damage);
    }

}
