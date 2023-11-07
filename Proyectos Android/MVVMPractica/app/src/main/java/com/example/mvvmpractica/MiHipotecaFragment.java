package com.example.mvvmpractica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmpractica.Entities.MiHipotecaViewModel;
import com.example.mvvmpractica.databinding.FragmentMiHipotecaBinding;

public class MiHipotecaFragment extends Fragment {
    private FragmentMiHipotecaBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentMiHipotecaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MiHipotecaViewModel viewModel = new ViewModelProvider(this).get(MiHipotecaViewModel.class);

        binding.calcular.setOnClickListener(v ->{
            double capital = 0;
            int plazo = 0;

            try{
                capital = Double.parseDouble(binding.capital.getText().toString());
            }catch (Exception e){
                binding.capital.setError("Introduzca un numero");
            }

            try{
                plazo = Integer.parseInt(binding.plazo.getText().toString());
            }catch (Exception e){
                binding.plazo.setError("Introduzca un numero");
            }

            viewModel.calcular(capital, plazo);

        });

        viewModel.cuota.observe(getViewLifecycleOwner(), (cuota) -> {
            binding.cuota.setText(String.format("%.2f", cuota) + "€");
        });

        viewModel.errorCapital.observe(getViewLifecycleOwner(), (capitalMinimo) -> {
            if(capitalMinimo != null){
                binding.capital.setError("El capital no puede ser inferior a " + capitalMinimo + "€");
            }else{
                binding.capital.setError(null);
            }
        });

        viewModel.errorPlazo.observe(getViewLifecycleOwner(), (plazoMinimo) -> {
            if(plazoMinimo != null){
                binding.plazo.setError("El plazo no puede ser inferior a " + plazoMinimo + " años");
            }else{
                binding.plazo.setError(null);
            }
        });

        viewModel.calculando.observe(getViewLifecycleOwner(), (calculando) -> {
            if (calculando){
                binding.calculando.setVisibility(View.VISIBLE);
                binding.cuota.setVisibility(View.GONE);
            }else{
                binding.calculando.setVisibility(View.GONE);
                binding.cuota.setVisibility(View.VISIBLE);
            }
        });

    }
}