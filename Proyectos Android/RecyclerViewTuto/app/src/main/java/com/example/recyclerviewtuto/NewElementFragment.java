package com.example.recyclerviewtuto;

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

import com.example.recyclerviewtuto.databinding.FragmentNewElementBinding;
import com.example.recyclerviewtuto.entities.Element;
import com.example.recyclerviewtuto.entities.ElementsViewModel;

public class NewElementFragment extends Fragment {
    private FragmentNewElementBinding binding;
    private ElementsViewModel elementsViewModel;
    private NavController navController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewElementBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        elementsViewModel = new ViewModelProvider(requireActivity()).get(ElementsViewModel.class);
        navController = Navigation.findNavController(view);

        binding.createButton.setOnClickListener( v->{
            elementsViewModel.insert(new Element(
                    binding.nameEditText.getText().toString(),
                    binding.descriptionEditText.getText().toString()
            ));
            navController.popBackStack();
        });
    }
}