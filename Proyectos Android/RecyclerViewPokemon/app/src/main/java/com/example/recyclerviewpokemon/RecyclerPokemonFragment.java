package com.example.recyclerviewpokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewpokemon.databinding.FragmentRecyclerPokemonBinding;
import com.example.recyclerviewpokemon.databinding.PokemonViewHolderBinding;
import com.example.recyclerviewpokemon.entities.Pokemon;
import com.example.recyclerviewpokemon.entities.PokemonsViewModel;

import java.util.List;

public class RecyclerPokemonFragment extends Fragment {
    private FragmentRecyclerPokemonBinding binding;
    private NavController navController;
    private PokemonsViewModel pokemonsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecyclerPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pokemonsViewModel = new ViewModelProvider(requireActivity()).get(PokemonsViewModel.class);
        PokemonsAdapter pokemonAdapter = new PokemonsAdapter();
        navController = Navigation.findNavController(view);

        binding.addPokemonButton.setOnClickListener( v -> navController.navigate(R.id.action_recyclerPokemonFragment_to_newPokemonFragment));
        binding.pokemonRecycler.setAdapter(pokemonAdapter);
        pokemonsViewModel.obtain().observe(getViewLifecycleOwner(), pokemonAdapter::setList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pokemonsViewModel.delete(pokemonAdapter.getElement(viewHolder.getAdapterPosition()));
                pokemonAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(binding.pokemonRecycler);

    }

    class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder>{
        class PokemonViewHolder extends RecyclerView.ViewHolder{
            PokemonViewHolderBinding binding;
            public PokemonViewHolder(PokemonViewHolderBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
        private List<Pokemon> pokemons;
        @NonNull
        @Override
        public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PokemonViewHolder(PokemonViewHolderBinding.inflate(
                    getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
            Pokemon pokemon = pokemons.get(position);
            holder.binding.image.setImageURI(pokemon.image);
            holder.binding.name.setText(pokemon.name);
            holder.binding.code.setText(String.format("%04d", pokemon.code));
            holder.itemView.setOnClickListener( v -> {
                pokemonsViewModel.select(pokemon);
                navController.navigate(R.id.action_recyclerPokemonFragment_to_showPokemonFragment);
            });
        }

        @Override
        public int getItemCount() {
            return pokemons.size();
        }
        public Pokemon getElement(int position){
            return pokemons.get(position);
        }

        public void setList(List<Pokemon> elements){
            this.pokemons = elements;
        }
    }
}