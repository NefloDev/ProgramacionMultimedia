package com.example.livedatatuto.Entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.livedatatuto.R;

import kotlin.jvm.functions.Function1;

public class EntrenadorViewModel extends AndroidViewModel {
    Entrenador entrenador;
    LiveData<Integer> ejercicioLiveData;
    LiveData<String> repeticionLiveData;
    public EntrenadorViewModel(@NonNull Application application) {
        super(application);
        entrenador = new Entrenador();
        ejercicioLiveData = Transformations.switchMap(entrenador.ordenLiveData, new Function1<>() {
            String ejercicioAnterior;
            @Override
            public LiveData<Integer> invoke(String orden) {
                String ejercicio = orden.split(":")[0];

                if (!ejercicio.equals(ejercicioAnterior)) {
                    ejercicioAnterior = ejercicio;
                    int imagen;

                    switch (ejercicio) {
                        case "EJERCICIO 2":
                            imagen = R.drawable.e2;
                            break;
                        case "EJERCICIO 3":
                            imagen = R.drawable.e3;
                            break;
                        case "EJERCICIO 4":
                            imagen = R.drawable.e4;
                            break;
                        case "EJERCICIO 1":
                        default:
                            imagen = R.drawable.e1;
                            break;
                    }
                    return new MutableLiveData<>(imagen);
                }
                return null;
            }
        });
        repeticionLiveData = Transformations.switchMap(entrenador.ordenLiveData, (orden) -> new MutableLiveData<>(orden.split(":")[1]));
    }

    public LiveData<Integer> obtenerEjercicio(){
        return ejercicioLiveData;
    }

    public LiveData<String> obtenerRepeticion(){
        return repeticionLiveData;
    }

}
