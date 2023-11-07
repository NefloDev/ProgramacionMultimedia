package com.example.mvvmpractica.Entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MiHipotecaViewModel extends AndroidViewModel {

    protected Executor executor;
    protected SimuladorHipoteca sim;
    public MutableLiveData<Double> cuota = new MutableLiveData<>();
    public MutableLiveData<Double> errorCapital = new MutableLiveData<>();
    public MutableLiveData<Integer> errorPlazo = new MutableLiveData<>();
    public MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public MiHipotecaViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        sim = new SimuladorHipoteca();
    }

    public void calcular(double capital, int plazo){
        final SimuladorHipoteca.Solicitud solicitud = new SimuladorHipoteca.Solicitud(capital, plazo);

        executor.execute(() -> sim.calcular(solicitud, new SimuladorHipoteca.Callback() {
            @Override
            public void cuandoEsteCalculadaLaCuota(double cuotaRestante) {
                errorCapital = null;
                errorPlazo = null;
                cuota.postValue(cuotaRestante);
            }

            @Override
            public void cuandoHayaErrorDeCapitalInferiorAlMinimo(double capitalMinimo) {
                errorCapital.postValue(capitalMinimo);
            }

            @Override
            public void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazoMinimo) {
                errorPlazo.postValue(plazoMinimo);
            }

            @Override
            public void cuandoEmpieceElCalculo() {
                calculando.postValue(true);
            }

            @Override
            public void cuandoFinaliceElCalculo() {
                calculando.postValue(false);
            }
        }));
    }
}
