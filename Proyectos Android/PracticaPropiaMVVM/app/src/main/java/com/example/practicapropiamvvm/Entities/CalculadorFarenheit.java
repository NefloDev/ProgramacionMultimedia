package com.example.practicapropiamvvm.Entities;

public class CalculadorFarenheit {
    public static class Solicitud{
        public double celsius;

        public Solicitud(double celsius){
            this.celsius = celsius;
        }
    }

    interface Callback {
        void cuandoEstenCalculadosLosFarenheit(double celsius);
        void cuandoHayaErrorDeCelsiusInferiorAlMinimo(double celsiusMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    private final double INDICE = 1.8;

    public void calcular(Solicitud solicitud, Callback callback){

        callback.cuandoEmpieceElCalculo();

        double celsiusMinimo = 0;
        boolean error = false;
        try{
            Thread.sleep(2500);
            celsiusMinimo = -273.15;
        }catch (InterruptedException ignored){}

        if (solicitud.celsius < celsiusMinimo){
            callback.cuandoHayaErrorDeCelsiusInferiorAlMinimo(celsiusMinimo);
            error = true;
        }

        if(!error){
            callback.cuandoEstenCalculadosLosFarenheit((solicitud.celsius * INDICE) + 32);
        }

        callback.cuandoFinaliceElCalculo();
    }

}
