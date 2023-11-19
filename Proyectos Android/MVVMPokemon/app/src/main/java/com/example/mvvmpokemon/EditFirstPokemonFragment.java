package com.example.mvvmpokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mvvmpokemon.Entities.PokemonCreate;
import com.example.mvvmpokemon.Entities.PokemonCreateViewModel;
import com.example.mvvmpokemon.databinding.FragmentEditFirstPokemonBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EditFirstPokemonFragment extends Fragment {
    private FragmentEditFirstPokemonBinding binding;
    private PokemonCreate.Pokemon pokemon1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditFirstPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.backToMainFromFirstPok.setOnClickListener(v -> navController.navigate(R.id.action_editPokemonFragment_to_mainMenu));

        final PokemonCreateViewModel viewModel = PokemonCreateViewModel.getInstance(requireActivity().getApplication());
        viewModel.initErrors();

        binding.checkButton.setOnClickListener(v -> {
            String integerError = "You must enter a number";
            boolean error = false;
            String name = null;
            int hp = -1, atk = -1, def = -1, spAtk = -1, spDef = -1;
            name = binding.nameIn1.getText().toString();
            if(name.isEmpty()){
                binding.nameIn1.setError("You must enter something");
                error = true;
            }
            try {
                hp = Integer.parseInt(binding.hpIn1.getText().toString());
            }catch (Exception e){
                binding.hpIn1.setError(integerError);
                error = true;
            }
            try {
                atk = Integer.parseInt(binding.atkIn1.getText().toString());
            }catch (Exception e){
                binding.atkIn1.setError(integerError);
                error = true;
            }
            try {
                def = Integer.parseInt(binding.defIn1.getText().toString());
            }catch (Exception e){
                binding.defIn1.setError(integerError);
                error = true;
            }
            try {
                spAtk = Integer.parseInt(binding.spAtkIn1.getText().toString());
            }catch (Exception e){
                binding.spAtkIn1.setError(integerError);
                error = true;
            }
            try {
                spDef = Integer.parseInt(binding.spDefIn1.getText().toString());
            }catch (Exception e){
                binding.spDefIn1.setError(integerError);
                error = true;
            }

            if(!error){
                viewModel.setPokemon1(name, hp, atk, def, spAtk, spDef);
            }
        });

        viewModel.errorName.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.nameIn1.setError(error);
            }
        });
        viewModel.errorHp.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.hpIn1.setError(error);
            }
        });
        viewModel.errorAtk.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.atkIn1.setError(error);
            }
        });
        viewModel.errorDef.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.defIn1.setError(error);
            }
        });
        viewModel.errorSpAtk.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.spAtkIn1.setError(error);
            }
        });
        viewModel.errorSpDef.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.spDefIn1.setError(error);
            }
        });

        viewModel.creating.observe(getViewLifecycleOwner(), (creating) -> {
             binding.checkButton.setVisibility(creating ? View.GONE : View.VISIBLE);
             binding.progressBar2.setVisibility(creating ? View.VISIBLE : View.GONE);
             if(!creating && viewModel.getPokemon1().isInitialized()){
                 navController.navigate(R.id.action_editPokemonFragment_to_editSecondPokemonFragment);
                 Toast.makeText(getContext(), "Created pokemon " + viewModel.getPokemon1().getValue().name, Toast.LENGTH_SHORT).show();
             }
        });
    }
}