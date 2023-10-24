package com.example.ejercicio2pmm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
public class Onboarding1Fragment extends Fragment {
    Button botonSiguiente;
    NavController navController;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        botonSiguiente = view.findViewById(R.id.botonSiguiente);

        botonSiguiente.setOnClickListener(v -> navController.navigate(R.id.action_onboarding1Fragment_to_onboarding2Fragment));
    }
}