package com.example.mvvmpokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmpokemon.databinding.FragmentMainMenuBinding;

public class MainMenu extends Fragment {
    private FragmentMainMenuBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.playImage.setOnClickListener(v -> navController.navigate(R.id.action_mainMenu_to_pokemonBattleFragment));
        binding.editImage.setOnClickListener(v -> navController.navigate(R.id.action_mainMenu_to_editPokemonFragment));
    }

}