package com.example.mvvmpokemon.Entities;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PokemonBattle {

    public interface BattleListener {
        void onTurn1(int action);
        void onTurn2(int action);
        void onBattleEnd(String result);
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> fighting;

    public void startBattle(PokemonCreate.Pokemon pokemon1, PokemonCreate.Pokemon pokemon2, BattleListener listener) {
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

    private void performTurn1(PokemonCreate.Pokemon attacker, PokemonCreate.Pokemon defender, BattleListener listener) {
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

    private void performTurn2(PokemonCreate.Pokemon attacker, PokemonCreate.Pokemon defender, BattleListener listener) {
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
