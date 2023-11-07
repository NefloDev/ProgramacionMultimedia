package com.example.mvvmpractica.Entities;

public class SimuladorHipoteca {
    public static class Solicitud{
        public double capital;
        public int plazo;

        public Solicitud(double capital, int plazo){
            this.capital = capital;
            this.plazo = plazo;
        }
    }

    interface Callback {
        void cuandoEsteCalculadaLaCuota(double cuota);
        void cuandoHayaErrorDeCapitalInferiorAlMinimo(double capitalMinimo);
        void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazoMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    private final double INTERES = 0.01605;

    public void calcular(Solicitud solicitud, Callback callback){

        callback.cuandoEmpieceElCalculo();

        double capitalMinimo = 0;
        int plazoMinimo = 0;
        boolean error = false;
        try{
            Thread.sleep(2500);
            capitalMinimo = 1000;
            plazoMinimo = 2;
        }catch (InterruptedException ignored){}

        if (solicitud.capital < capitalMinimo){
            callback.cuandoHayaErrorDeCapitalInferiorAlMinimo(capitalMinimo);
            error = true;
        }
        if (solicitud.plazo < plazoMinimo){
            callback.cuandoHayaErrorDePlazoInferiorAlMinimo(plazoMinimo);
            error = true;
        }

        if(!error){
            callback.cuandoEsteCalculadaLaCuota(solicitud.capital * INTERES / 12 / ( 1 - Math.pow( 1 + ( INTERES / 12 ), -solicitud.plazo * 12 ) ));
        }

        callback.cuandoFinaliceElCalculo();
    }

}
