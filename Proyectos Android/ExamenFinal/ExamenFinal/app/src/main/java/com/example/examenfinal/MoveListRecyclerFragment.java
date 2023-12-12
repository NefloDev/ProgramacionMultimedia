package com.example.examenfinal;

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
import com.example.examenfinal.databinding.FragmentMoveListRecyclerBinding;
import com.example.examenfinal.databinding.ViewholderMoveListBinding;

import com.example.examenfinal.models.MoveListItem;

import java.util.List;

public class MoveListRecyclerFragment extends Fragment {
    private FragmentMoveListRecyclerBinding binding;
    private MovesViewModel movesViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMoveListRecyclerBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movesViewModel = new ViewModelProvider(requireActivity()).get(MovesViewModel.class);
        navController = Navigation.findNavController(view);
        MovesAdapter movesAdapter = new MovesAdapter();
        binding.recyclerView.setAdapter(movesAdapter);

        // Cuando cambia el viewmodel se actualiza la lista con setList(List<MoveListItem> moveList)
        movesViewModel.getAll().observe(getViewLifecycleOwner(), movesAdapter::setList);
    }

    class MovesAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        List<MoveListItem> moveList;

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(ViewholderMoveListBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            MoveListItem element = moveList.get(position);
            holder.binding.setMove(element);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movesViewModel.select(element);
                    navController.navigate(R.id.action_moveListRecyclerFragment_to_moveDetailFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return moveList != null ? moveList.size() : 0;
        }

        public void setList(List<MoveListItem> moveList){
            this.moveList = moveList;
            notifyDataSetChanged();
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderMoveListBinding binding;

        public ItemViewHolder(ViewholderMoveListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}