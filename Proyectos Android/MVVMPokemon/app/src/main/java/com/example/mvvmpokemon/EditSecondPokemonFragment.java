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

import com.example.mvvmpokemon.Entities.PokemonCreate;
import com.example.mvvmpokemon.Entities.PokemonCreateViewModel;
import com.example.mvvmpokemon.databinding.FragmentEditSecondPokemonBinding;

public class EditSecondPokemonFragment extends Fragment {
    private FragmentEditSecondPokemonBinding binding;
    private PokemonCreate.Pokemon pokemon2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditSecondPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        binding.backToFirstFromSecond.setOnClickListener(v -> navController.navigate(R.id.action_editSecondPokemonFragment_to_editPokemonFragment));

        final PokemonCreateViewModel viewModel = new ViewModelProvider(this).get(PokemonCreateViewModel.class);

        binding.finalCheckButton.setOnClickListener(v -> {
            String integerError = "You must enter a number";
            boolean error = false;
            String name;
            int hp = -1, atk = -1, def = -1, spAtk = -1, spDef = -1;
            name = binding.nameIn2.getText().toString();
            if(name.isEmpty()){
                binding.nameIn2.setError("You must enter something");
                error = true;
            }
            try {
                hp = Integer.parseInt(binding.hpIn2.getText().toString());
            }catch (Exception e){
                binding.hpIn2.setError(integerError);
                error = true;
            }
            try {
                atk = Integer.parseInt(binding.atkIn2.getText().toString());
            }catch (Exception e){
                binding.atkIn2.setError(integerError);
                error = true;
            }
            try {
                def = Integer.parseInt(binding.defIn2.getText().toString());
            }catch (Exception e){
                binding.defIn2.setError(integerError);
                error = true;
            }
            try {
                spAtk = Integer.parseInt(binding.spAtkIn2.getText().toString());
            }catch (Exception e){
                binding.spAtkIn2.setError(integerError);
                error = true;
            }
            try {
                spDef = Integer.parseInt(binding.spDefIn2.getText().toString());
            }catch (Exception e){
                binding.spDefIn2.setError(integerError);
                error = true;
            }

            if(!error){
                viewModel.create(name, hp, atk, def, spAtk, spDef);
            }
        });

        viewModel.errorName.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.nameIn2.setError(error);
            }
        });
        viewModel.errorHp.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.hpIn2.setError(error);
            }
        });
        viewModel.errorAtk.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.atkIn2.setError(error);
            }
        });
        viewModel.errorDef.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.defIn2.setError(error);
            }
        });
        viewModel.errorSpAtk.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.spAtkIn2.setError(error);
            }
        });
        viewModel.errorSpDef.observe(getViewLifecycleOwner(), (error) -> {
            if(error != null){
                binding.spDefIn2.setError(error);
            }
        });

        viewModel.creating.observe(getViewLifecycleOwner(), (creating) -> {
            binding.finalCheckButton.setVisibility(creating ? View.GONE : View.VISIBLE);
            binding.progressBar.setVisibility(creating ? View.VISIBLE : View.GONE);
            if(!creating && viewModel.getPokemon().isInitialized()){
                navController.navigate(R.id.action_editSecondPokemonFragment_to_mainMenu);
            }
        });

        pokemon2 = viewModel.getPokemon().getValue();
    }
}