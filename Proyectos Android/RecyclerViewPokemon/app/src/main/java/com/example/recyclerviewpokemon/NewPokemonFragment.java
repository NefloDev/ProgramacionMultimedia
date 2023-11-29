package com.example.recyclerviewpokemon;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewpokemon.databinding.FragmentNewPokemonBinding;
import com.example.recyclerviewpokemon.entities.Pokemon;
import com.example.recyclerviewpokemon.entities.PokemonsViewModel;

public class NewPokemonFragment extends Fragment {
    private FragmentNewPokemonBinding binding;
    private PokemonsViewModel pokemonsViewModel;
    private NavController navController;
    private Uri selectedImage;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentNewPokemonBinding.inflate(inflater, container, false)).getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pokemonsViewModel = new ViewModelProvider(requireActivity()).get(PokemonsViewModel.class);
        navController = Navigation.findNavController(view);

        binding.createButton.setOnClickListener(v -> {
            if (selectedImage != null) {
                if(checkIfFilled()){
                    pokemonsViewModel.insert(new Pokemon(
                            Integer.parseInt(binding.codeInput.getText().toString()),
                            binding.nameInput.getText().toString(),
                            binding.typesInput.getText().toString(),
                            binding.speciesInput.getText().toString(),
                            Double.parseDouble(binding.heightInput.getText().toString()),
                            Double.parseDouble(binding.weightInput.getText().toString()),
                            selectedImage
                    ));
                    navController.popBackStack();
                }
            } else {
                getImage();
            }
        });

        binding.insertImage.setOnClickListener(v -> {
            getImage();
        });
    }
    private void getImage(){
        Intent i =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Está deprecated pero la opción alternativa no me funciona, tengo que seguir investigandola
        startActivityForResult(i, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            binding.testImg.setImageURI(selectedImage);
        }
    }

    private boolean checkIfFilled(){
        boolean error = false;
        if(binding.codeInput.getText().toString().isEmpty()){
            binding.codeInput.setError("Enter something");
            error = true;
        }
        if (binding.nameInput.getText().toString().isEmpty()){
            binding.nameInput.setError("Enter something");
            error = true;
        }
        if (binding.typesInput.getText().toString().isEmpty()){
            binding.typesInput.setError("Enter something");
            error = true;
        }
        if (binding.speciesInput.getText().toString().isEmpty()){
            binding.speciesInput.setError("Enter something");
            error = true;
        }
        if (binding.heightInput.getText().toString().isEmpty()){
            binding.heightInput.setError("Enter something");
            error = true;
        }
        if (binding.weightInput.getText().toString().isEmpty()){
            binding.weightInput.setError("Enter something");
            error = true;
        }
        return !error;
    }
}