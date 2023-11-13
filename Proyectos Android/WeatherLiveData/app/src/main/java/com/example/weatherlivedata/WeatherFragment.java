package com.example.weatherlivedata;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.weatherlivedata.ModelView.WeatherViewModel;
import com.example.weatherlivedata.databinding.FragmentWeatherBinding;

public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WeatherViewModel weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        weatherViewModel.getWeatherLiveData().observe(getViewLifecycleOwner(), weather -> binding.weatherText.setText(weather));
        weatherViewModel.getWeatherImageLiveData().observe(getViewLifecycleOwner(), (Integer weather) -> {
            Glide.with(WeatherFragment.this).load(weather).into(binding.weatherImage);
        });

    }
}