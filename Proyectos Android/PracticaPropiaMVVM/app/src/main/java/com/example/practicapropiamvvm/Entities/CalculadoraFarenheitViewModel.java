package com.example.practicapropiamvvm.Entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CalculadoraFarenheitViewModel extends AndroidViewModel {

    protected Executor executor;
    protected CalculadorFarenheit calc;
    public MutableLiveData<Double> farenheit = new MutableLiveData<>();
    public MutableLiveData<Double> errorCelsius = new MutableLiveData<>();
    public MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public CalculadoraFarenheitViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        calc = new CalculadorFarenheit();
    }

    public void calcular(double celsius){
        final CalculadorFarenheit.Solicitud solicitud = new CalculadorFarenheit.Solicitud(celsius);

        executor.execute(() -> calc.calcular(solicitud, new CalculadorFarenheit.Callback() {
            @Override
            public void cuandoEstenCalculadosLosFarenheit(double farenheitResultado) {
                errorCelsius = null;
                farenheit.postValue(farenheitResultado);
            }

            @Override
            public void cuandoHayaErrorDeCelsiusInferiorAlMinimo(double celsiusMinimo) {
                errorCelsius.postValue(celsiusMinimo);
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
