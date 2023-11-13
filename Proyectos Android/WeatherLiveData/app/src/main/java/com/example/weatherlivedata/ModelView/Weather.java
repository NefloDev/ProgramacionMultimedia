package com.example.weatherlivedata.ModelView;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Weather {

    interface WeatherListener{
        void whenWeatherChanges(String weather);
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> changingWeather;
    LiveData<String>weatherChangeLiveData = new LiveData<String>() {

        @Override
        protected void onActive() {
            super.onActive();
            startWeatherChanging(this::postValue);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            stopChangingWeather();
        }
    };

    void startWeatherChanging(WeatherListener weatherListener){

        if(changingWeather == null || changingWeather.isCancelled()){
            changingWeather = scheduler.scheduleAtFixedRate(new Runnable() {
                int i = 0;
                List<String> weathers = List.of("Sunny", "Cloudy", "Rainy", "Windy");
                @Override
                public void run() {
                    if(i == weathers.size()){
                        i = 0;
                    }
                    weatherListener.whenWeatherChanges(weathers.get(i));
                    i++;
                }
            }, 0, 1, TimeUnit.SECONDS);
        }

    }

    void stopChangingWeather(){ if(changingWeather!=null) changingWeather.cancel(true);}

}