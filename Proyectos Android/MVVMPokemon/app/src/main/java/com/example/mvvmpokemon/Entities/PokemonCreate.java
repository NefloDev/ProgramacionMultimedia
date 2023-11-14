package com.example.mvvmpokemon.Entities;

public class PokemonCreate {
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

}
