package com.example.examenfinal;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.examenfinal.databinding.FragmentItemDetailBinding;
import com.example.examenfinal.models.Item;
import com.example.examenfinal.models.Move;


public class ItemDetailFragment extends Fragment {

    private FragmentItemDetailBinding binding;
    ItemsViewModel itemsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsViewModel = new ViewModelProvider(requireActivity()).get(ItemsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsViewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(Item m) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.itemValue.setText(m.getName());
                binding.categoryValue.setText(m.getCategory());
                binding.costValue.setText(String.valueOf(m.getCost()));
                binding.effectList.setText(m.getItemEffects());
                Glide.with(requireActivity())
                                .load(m.getSprites().getUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                binding.progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                binding.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                                        .into(binding.image);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentItemDetailBinding.inflate(inflater, container, false)).getRoot();

    }

}