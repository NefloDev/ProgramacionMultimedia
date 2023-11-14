package com.example.mvvmpokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmpokemon.Entities.PokemonCreate;
import com.example.mvvmpokemon.Entities.PokemonCreateViewModel;
import com.example.mvvmpokemon.databinding.FragmentPokemonBattleBinding;

public class PokemonBattleFragment extends Fragment {
    private FragmentPokemonBattleBinding binding;
    private PokemonCreate.Pokemon pokemon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokemonBattleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.redoButton.setOnClickListener(v -> navController.navigate(R.id.action_pokemonBattleFragment_to_mainMenu));

        PokemonCreateViewModel viewModel = new ViewModelProvider(requireActivity()).get(PokemonCreateViewModel.class);
        //NO FUNCA: pokemon = viewModel.pokemonLiveData.getValue();

    }
}