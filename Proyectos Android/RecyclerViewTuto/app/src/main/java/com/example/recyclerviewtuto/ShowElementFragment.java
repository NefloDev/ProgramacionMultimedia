package com.example.recyclerviewtuto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewtuto.databinding.FragmentShowElementBinding;
import com.example.recyclerviewtuto.entities.ElementsViewModel;

public class ShowElementFragment extends Fragment {
    private FragmentShowElementBinding binding;
    private ElementsViewModel elementsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShowElementBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        elementsViewModel = new ViewModelProvider(requireActivity()).get(ElementsViewModel.class);
        elementsViewModel.selected().observe(getViewLifecycleOwner(), element -> {
            binding.selectedName.setText(element.name);
            binding.selectedDescription.setText(element.description);
            binding.selectedRating.setRating(element.valoration);

            binding.selectedRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) ->{
                if (fromUser){
                    elementsViewModel.update(element, rating);
                }
            });
        });
    }
}