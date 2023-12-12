package com.example.examenfinal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.examenfinal.models.Item;
import com.example.examenfinal.models.ItemListItem;
import com.example.examenfinal.pokeapi.PokeAPI;

import java.util.List;

public class ItemsViewModel extends AndroidViewModel {
    MutableLiveData<Item> selectedItemMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<ItemListItem>> listElementosMutableLiveData = new MutableLiveData<>();
    ItemListItem selected;

    public ItemsViewModel(@NonNull Application application) {
        super(application);
        PokeAPI.getItemList(listElementosMutableLiveData);
    }

    MutableLiveData<List<ItemListItem>> getAll(){
        return listElementosMutableLiveData;
    }

    public void select(ItemListItem itemListItem) {
        selected = itemListItem;
    }

    public void select(String id){
        selected = new ItemListItem();
        selected.setName(id);
    }

    public MutableLiveData<Item> getSelected() {
        PokeAPI.getItem(selected.getName(), selectedItemMutableLiveData);
        return selectedItemMutableLiveData;
    }
}
