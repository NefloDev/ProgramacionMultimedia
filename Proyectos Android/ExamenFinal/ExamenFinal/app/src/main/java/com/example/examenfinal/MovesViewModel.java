package com.example.examenfinal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.examenfinal.models.Move;
import com.example.examenfinal.models.MoveListItem;
import com.example.examenfinal.pokeapi.PokeAPI;

import java.util.List;

public class MovesViewModel extends AndroidViewModel {
    MutableLiveData<Move> selectedMoveMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<MoveListItem>> listElementosMutableLiveData = new MutableLiveData<>();
    MoveListItem selected;

    public MovesViewModel(@NonNull Application application) {
        super(application);
        PokeAPI.getMoveList(listElementosMutableLiveData);
    }

    MutableLiveData<List<MoveListItem>> getAll(){
        return listElementosMutableLiveData;
    }

    public void select(MoveListItem moveListItem) {
        selected = moveListItem;
    }

    public MutableLiveData<Move> getSelected() {
        PokeAPI.getMove(selected.getName(), selectedMoveMutableLiveData);
        return selectedMoveMutableLiveData;
    }
}
