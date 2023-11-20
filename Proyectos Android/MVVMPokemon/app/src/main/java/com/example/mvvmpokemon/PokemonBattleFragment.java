package com.example.mvvmpokemon;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmpokemon.Entities.PokemonViewModel;
import com.example.mvvmpokemon.databinding.FragmentPokemonBattleBinding;

public class PokemonBattleFragment extends Fragment {
    private FragmentPokemonBattleBinding binding;
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
        binding.winnerText.setVisibility(View.GONE);
        binding.attackText.setVisibility(View.GONE);

        PokemonViewModel viewModel = PokemonViewModel.getInstance(requireActivity().getApplication());

        if(viewModel.getPokemon1() != null && viewModel.getPokemon2() != null){
            viewModel.getPokemon1().observe(getViewLifecycleOwner(), (pokemon) -> {
                binding.pokemon1Name.setText(firstUpperString(pokemon.name));
                binding.pokemon1ValHp.setText(String.valueOf(pokemon.hp));
                binding.pokemon1ValAtk.setText(String.valueOf(pokemon.atk));
                binding.pokemon1ValDef.setText(String.valueOf(pokemon.def));
                binding.pokemon1ValSpAtk.setText(String.valueOf(pokemon.spAtk));
                binding.pokemon1ValSpDef.setText(String.valueOf(pokemon.spDef));
            });
            viewModel.getPokemon2().observe(getViewLifecycleOwner(), (pokemon) -> {
                binding.pokemon2Name.setText(firstUpperString(pokemon.name));
                binding.pokemon2ValHp.setText(String.valueOf(pokemon.hp));
                binding.pokemon2ValAtk.setText(String.valueOf(pokemon.atk));
                binding.pokemon2ValDef.setText(String.valueOf(pokemon.def));
                binding.pokemon2ValSpAtk.setText(String.valueOf(pokemon.spAtk));
                binding.pokemon2ValSpDef.setText(String.valueOf(pokemon.spDef));
            });

            binding.battleButton.setOnClickListener(v -> {
                viewModel.startBattle(viewModel.getPokemon1().getValue(), viewModel.getPokemon2().getValue());
                binding.battleButton.setVisibility(View.GONE);
                binding.attackText.setVisibility(View.VISIBLE);
            });

            viewModel.damage1.observe(getViewLifecycleOwner(), (damage) -> {
                int dmg = Integer.parseInt(damage);
                boolean special = dmg%1000==0;
                if(special){
                    dmg = dmg/1000;
                    binding.pokemon2ValDamage.setText("-" + dmg);
                    binding.pokemon1ValDamage.setText("");}
                String attackMessage;
                attackMessage = special ? " did a special attack!" : " attacked!";
                binding.attackText.setText(binding.pokemon1Name.getText().toString() + attackMessage);
                if(dmg == 0){
                    binding.attackText.append(" But " + binding.pokemon2Name.getText().toString() + " defended!");
                }
                int updateHp = Integer.parseInt(binding.pokemon2ValHp.getText().toString()) - dmg;
                binding.pokemon2ValHp.setText(String.valueOf(updateHp < 0 ? 0 : updateHp));
            });

            viewModel.damage2.observe(getViewLifecycleOwner(), (damage) -> {
                int dmg = Integer.parseInt(damage);
                boolean special = dmg%1000==0;
                if(special){
                    dmg = dmg/1000;
                    binding.pokemon1ValDamage.setText("-" + dmg);
                    binding.pokemon2ValDamage.setText("");}
                String attackMessage;
                attackMessage = special ? " did a special attack!" : " attacked!";
                binding.attackText.setText(binding.pokemon2Name.getText().toString() + attackMessage);
                if(dmg == 0){
                    binding.attackText.append(" But " + binding.pokemon1Name.getText().toString() + " defended!");
                }
                int updateHp = Integer.parseInt(binding.pokemon1ValHp.getText().toString()) - dmg;
                binding.pokemon1ValHp.setText(String.valueOf(updateHp < 0 ? 0 : updateHp));
            });

            viewModel.winner.observe(getViewLifecycleOwner(), (winner) -> {
                binding.winnerText.setVisibility(View.VISIBLE);
                binding.winnerText.setText(winner);
            });

        }else{
            final int action = viewModel.getPokemon1() == null ?
                    R.id.action_pokemonBattleFragment_to_editPokemonFragment :
                    R.id.action_pokemonBattleFragment_to_editSecondPokemonFragment;
            new AlertDialog.Builder(getContext()).setTitle("Oh oh...").setMessage("Looks like you still have pokemons left to create")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> navController.navigate(action))
                    .show();
        }
    }

    private String firstUpperString(String text){
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}