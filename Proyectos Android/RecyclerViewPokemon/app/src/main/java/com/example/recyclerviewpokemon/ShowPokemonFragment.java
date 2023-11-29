package com.example.recyclerviewpokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewpokemon.databinding.FragmentShowPokemonBinding;
import com.example.recyclerviewpokemon.entities.PokemonsViewModel;

public class ShowPokemonFragment extends Fragment {
    private FragmentShowPokemonBinding binding;
    private PokemonsViewModel pokemonsViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentShowPokemonBinding.inflate(inflater, container, false)).getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pokemonsViewModel = new ViewModelProvider(requireActivity()).get(PokemonsViewModel.class);
        pokemonsViewModel.selected().observe(getViewLifecycleOwner(), pokemon -> {
            binding.pokemonImage.setImageURI(pokemon.image);
            binding.pokemonName.setText(pokemon.name);
            binding.pokemonCode.setText("Code: " + String.format("%04d", pokemon.code));
            binding.pokemonTypes.setText("Types: " + pokemon.types);
            binding.pokemonSpecies.setText("Species: " + pokemon.species);
            binding.pokemonHeight.setText("Height: " + pokemon.height + "m");
            binding.pokemonWeight.setText("Weight: " + pokemon.height + "kg");
        });
    }
}