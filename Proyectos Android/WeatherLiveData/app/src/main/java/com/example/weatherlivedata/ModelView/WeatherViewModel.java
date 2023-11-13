package com.example.weatherlivedata.ModelView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.weatherlivedata.R;

import kotlin.jvm.functions.Function1;

public class WeatherViewModel extends AndroidViewModel {
    Weather weather;
    LiveData<String> weatherLiveData;
    LiveData<Integer> weatherImageLiveData;
    LiveData<Integer> weatherImageColourLiveData;

    public WeatherViewModel(@NonNull Application application) {
        super(application);

        weather = new Weather();
        weatherLiveData = Transformations.switchMap(weather.weatherChangeLiveData, MutableLiveData::new);
        weatherImageLiveData = Transformations.switchMap(weather.weatherChangeLiveData, new Function1<>() {
            String lastWeather;
            int image;
            @Override
            public LiveData<Integer> invoke(String weather) {
                if (!weather.equals(lastWeather)) {
                    switch (weather) {
                        case "Sunny":
                            image = R.drawable.sunnyicon;
                            break;
                        case "Cloudy":
                            image = R.drawable.cloudyicon;
                            break;
                        case "Rainy":
                            image = R.drawable.rainyicon;
                            break;
                        case "Windy":
                            image = R.drawable.windyicon;
                            break;
                        default:
                            image = R.drawable.sunnyicon;
                    }
                }
                return new MutableLiveData<>(image);
            }
        });

    }

    public LiveData<String> getWeatherLiveData(){
        return weatherLiveData;
    }

    public LiveData<Integer> getWeatherImageLiveData(){
        return weatherImageLiveData;
    }
}
