package com.example.examenfinal;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.examenfinal.databinding.FragmentItemListRecyclerBinding;
import com.example.examenfinal.databinding.ViewholderItemListBinding;
import com.example.examenfinal.models.ItemListItem;
import com.example.examenfinal.models.MoveListItem;

import java.util.List;

public class ItemListRecyclerFragment extends Fragment {
    private FragmentItemListRecyclerBinding binding;
    private ItemsViewModel itemsViewModel;
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemListRecyclerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsViewModel = new ViewModelProvider(requireActivity()).get(ItemsViewModel.class);
        navController = Navigation.findNavController(view);
        ItemsAdapter movesAdapter = new ItemListRecyclerFragment.ItemsAdapter();
        binding.itemRecycler.setAdapter(movesAdapter);

        itemsViewModel.getAll().observe(getViewLifecycleOwner(), movesAdapter::setList);
    }

    class ItemsAdapter extends RecyclerView.Adapter<ItemListRecyclerFragment.ItemViewHolder> {
        List<ItemListItem> itemList;

        @NonNull
        @Override
        public ItemListRecyclerFragment.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemListRecyclerFragment.ItemViewHolder(ViewholderItemListBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemListRecyclerFragment.ItemViewHolder holder, int position) {
            holder.binding.progressBar.setVisibility(View.VISIBLE);
            ItemListItem element = itemList.get(position);
            holder.binding.name.setText(element.getName());
            Glide.with(requireActivity())
                            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/" + element.getName() + ".png")
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.binding.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.binding.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                                    .into(holder.binding.recyclerImage);

            holder.itemView.setOnClickListener(v -> {
                itemsViewModel.select(element);
                navController.navigate(R.id.action_itemListRecyclerFragment_to_itemDetailFragment);
            });
        }

        @Override
        public int getItemCount() {
            return itemList != null ? itemList.size() : 0;
        }

        public void setList(List<ItemListItem> itemList){
            this.itemList = itemList;
            notifyDataSetChanged();
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderItemListBinding binding;

        public ItemViewHolder(ViewholderItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}