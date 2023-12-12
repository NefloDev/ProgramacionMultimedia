package com.example.examenfinal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.examenfinal.databinding.FragmentMoveDetailBinding;
import com.example.examenfinal.models.Move;
import com.example.examenfinal.models.PokemonListItem;

import java.util.Random;
import java.util.stream.Collectors;


public class MoveDetailFragment extends Fragment {

    private FragmentMoveDetailBinding binding;
    MovesViewModel movesViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movesViewModel = new ViewModelProvider(requireActivity()).get(MovesViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movesViewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<Move>() {
            @Override
            public void onChanged(Move m) {
                binding.movementName.setText(m.getName());
                binding.accuracyValue.setText(m.getAccuracy());
                binding.pokemonList.setText(m.getPokemonsString());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentMoveDetailBinding.inflate(inflater, container, false)).getRoot();

    }

}