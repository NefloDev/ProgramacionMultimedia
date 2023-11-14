package com.example.navigationpractica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationpractica.GenericFragments.TabAFragment;
import com.example.navigationpractica.GenericFragments.TabBFragment;
import com.example.navigationpractica.GenericFragments.TabCFragment;
import com.example.navigationpractica.databinding.FragmentTabBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabFragment extends Fragment {
    private FragmentTabBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Para cambiar al fragment específico a partir del adaptador
        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position){
                    case 0: default:
                        return new TabAFragment();
                    case 1:
                        return new TabBFragment();
                    case 2:
                        return new TabCFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });
        //Cambiando el texto de los elementos que se muestra en la barra del tab layout
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0: default:
                    tab.setText("TAB A");
                    break;
                case 1:
                    tab.setText("TAB B");
                    break;
                case 2:
                    tab.setText("TAB C");
                    break;
            }
        }).attach();
    }
}