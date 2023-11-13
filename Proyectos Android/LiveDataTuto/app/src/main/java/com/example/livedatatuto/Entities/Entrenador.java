package com.example.livedatatuto.Entities;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Entrenador {

    interface EntrenadorListener{
        void cuandoDeLaOrden(String orden);
    }

    Random r = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> entrenando;
    LiveData<String> ordenLiveData = new LiveData<>() {
        @Override
        protected void onActive() {
            super.onActive();
            iniciarEntrenamiento(this::postValue);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            pararEntrenamiento();
        }
    };

    void iniciarEntrenamiento(EntrenadorListener entrenadorListener){
        if(entrenando == null || entrenando.isCancelled()){
            entrenando = scheduler.scheduleAtFixedRate(new Runnable() {
                int ejercicio;
                int repeticiones = -1;
                @Override
                public void run() {
                    if (repeticiones < 0){
                        repeticiones = r.nextInt(3) + 3;
                        ejercicio = r.nextInt(4) + 1;
                    }
                    entrenadorListener.cuandoDeLaOrden("EJERCICIO " + ejercicio + ": " + (repeticiones == 0 ? "CAMBIO" : repeticiones));
                    repeticiones--;
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararEntrenamiento(){
        if (entrenando != null) entrenando.cancel(true);
    }

}
