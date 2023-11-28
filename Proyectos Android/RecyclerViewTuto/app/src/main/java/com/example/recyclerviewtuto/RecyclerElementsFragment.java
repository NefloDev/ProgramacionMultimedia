package com.example.recyclerviewtuto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewtuto.databinding.ElementViewHolderBinding;
import com.example.recyclerviewtuto.databinding.FragmentRecyclerElementsBinding;
import com.example.recyclerviewtuto.entities.Element;
import com.example.recyclerviewtuto.entities.ElementsViewModel;

import java.util.List;

public class RecyclerElementsFragment extends Fragment {
    private FragmentRecyclerElementsBinding binding;
    private NavController navController;
    private ElementsViewModel elementsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecyclerElementsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        elementsViewModel = new ViewModelProvider(requireActivity()).get(ElementsViewModel.class);
        ElementsAdapter elementsAdapter = new ElementsAdapter();
        navController = Navigation.findNavController(view);

        binding.addElementButton.setOnClickListener( v -> navController.navigate(R.id.action_recyclerElementFragment_to_newElementFragment));
        binding.elementRecycler.setAdapter(elementsAdapter);
        elementsViewModel.obtain().observe(getViewLifecycleOwner(), elementsAdapter::setList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                elementsViewModel.delete(elementsAdapter.getElement(viewHolder.getAdapterPosition()));
                elementsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(binding.elementRecycler);

    }

    class ElementsAdapter extends RecyclerView.Adapter<ElementsAdapter.ElementViewHolder>{
        class ElementViewHolder extends RecyclerView.ViewHolder{
            ElementViewHolderBinding binding;
            public ElementViewHolder(ElementViewHolderBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
        private List<Element> elements;
        @NonNull
        @Override
        public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementViewHolder(ElementViewHolderBinding.inflate(
                    getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
            Element element = elements.get(position);
            holder.binding.name.setText(element.name);
            holder.binding.valoration.setRating(element.valoration);
            holder.binding.valoration.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                if (fromUser){
                    elementsViewModel.update(element, rating);
                }
            });
            holder.itemView.setOnClickListener( v -> {
                elementsViewModel.select(element);
                navController.navigate(R.id.action_recyclerElementFragment_to_showElementFragment);
            });
        }

        @Override
        public int getItemCount() {
            return elements.size();
        }
        public Element getElement(int position){
            return elements.get(position);
        }

        public void setList(List<Element> elements){
            this.elements = elements;
        }
    }
}