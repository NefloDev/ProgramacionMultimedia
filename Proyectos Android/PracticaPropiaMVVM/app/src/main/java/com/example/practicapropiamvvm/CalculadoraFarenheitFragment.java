package com.example.practicapropiamvvm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practicapropiamvvm.Entities.CalculadoraFarenheitViewModel;
import com.example.practicapropiamvvm.databinding.FragmentCalculadoraFarenheitBinding;

public class CalculadoraFarenheitFragment extends Fragment {
    private FragmentCalculadoraFarenheitBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentCalculadoraFarenheitBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CalculadoraFarenheitViewModel viewModel = new ViewModelProvider(this).get(CalculadoraFarenheitViewModel.class);

        binding.calcular.setOnClickListener(v ->{
            try{
                viewModel.calcular(Double.parseDouble(binding.celsius.getText().toString()));
            }catch (Exception e){
                binding.celsius.setError("Introduzca un numero");
            }
        });

        viewModel.farenheit.observe(getViewLifecycleOwner(), (farenheit) -> {
            binding.farenheit.setText(String.format("%.2f", farenheit) + "ºF");
        });

        viewModel.errorCelsius.observe(getViewLifecycleOwner(), (celsiusMinimo) -> {
            if(celsiusMinimo != null){
                binding.celsius.setError("Los grados no pueden ser inferior a " + celsiusMinimo + "ºC");
            }else{
                binding.celsius.setError(null);
            }
        });

        viewModel.calculando.observe(getViewLifecycleOwner(), (calculando) -> {
            if (calculando){
                binding.calculando.setVisibility(View.VISIBLE);
                binding.farenheit.setVisibility(View.GONE);
            }else{
                binding.calculando.setVisibility(View.GONE);
                binding.farenheit.setVisibility(View.VISIBLE);
            }
        });

    }
}