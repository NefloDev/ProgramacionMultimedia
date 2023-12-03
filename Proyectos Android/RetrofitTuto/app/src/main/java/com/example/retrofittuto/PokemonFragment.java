package com.example.retrofittuto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.retrofittuto.databinding.FragmentPokemonBinding;
import com.example.retrofittuto.entities.PokemonViewModel;

public class PokemonFragment extends Fragment {
    private FragmentPokemonBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PokemonViewModel viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        binding.offsetInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String offset) {
                viewModel.search(offset);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String offset) {
                return false;
            }
        });

        viewModel.responseMutableLiveData.observe(getViewLifecycleOwner(),
                apiResponse -> apiResponse.getResults().forEach(p -> Log.d("POKEMON", p.getName() + " - " + p.getUrl())));
    }
}