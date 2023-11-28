package com.example.recyclerviewtuto.entities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class ElementsViewModel extends AndroidViewModel {
    private ElementsRepo elementsRepo;
    MutableLiveData<ArrayList<Element>> listElementMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Element> selectedElement = new MutableLiveData<>();
    public ElementsViewModel(@NonNull Application application) {
        super(application);
        elementsRepo = new ElementsRepo();
        listElementMutableLiveData.setValue(elementsRepo.obtain());
    }
    public MutableLiveData<ArrayList<Element>> obtain(){return listElementMutableLiveData;}
    public void select(Element element){selectedElement.setValue(element);}
    public MutableLiveData<Element> selected(){return selectedElement;}
    public void insert(Element element){
        elementsRepo.insert(element, elmts -> listElementMutableLiveData.setValue(elmts));
    }
    public void delete(Element element){
        elementsRepo.delete(element, elmts -> listElementMutableLiveData.setValue(elmts));
    }
    public void update(Element element, float valoration){
        elementsRepo.update(element, valoration, elmts -> listElementMutableLiveData.setValue(elmts));
    }
}
