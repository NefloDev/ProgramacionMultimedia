package com.example.retrofittutokotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import com.example.retrofittutokotlin.databinding.FragmentPokemonBinding
import com.example.retrofittutokotlin.entities.PokemonViewModel

class PokemonFragment : Fragment() {
    private lateinit var binding : FragmentPokemonBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this@PokemonFragment)[PokemonViewModel::class.java]

        binding.offsetInput.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(offset: String): Boolean {
                viewModel.search(offset)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean { return false }

        })

        viewModel.responseMutableLiveData.observe(viewLifecycleOwner) {apiResponse ->
            apiResponse.getResults().forEach{p -> Log.d("POKEMON", p.getName() + " - " + p.getUrl())}}
    }
}