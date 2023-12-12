package com.example.examenfinal;

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

import com.example.examenfinal.databinding.FragmentMenuBinding;
import com.example.examenfinal.models.ItemListItem;

import java.util.Random;

public class MenuFragment extends Fragment {
    private FragmentMenuBinding binding;
    private NavController navController;
    private ItemsViewModel itemsViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        itemsViewModel = new ViewModelProvider(requireActivity()).get(ItemsViewModel.class);
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.movementListButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_menuFragment_to_moveListRecyclerFragment);
        });
        binding.itemListButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_menuFragment_to_itemListRecyclerFragment);
        });
        binding.randomItemButton.setOnClickListener(v -> {
            itemsViewModel.select(String.valueOf(new Random().nextInt((2110-1)+1)));
            navController.navigate(R.id.action_menuFragment_to_itemDetailFragment);
        });
    }
}