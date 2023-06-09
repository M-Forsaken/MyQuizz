package com.example.myquizzz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewItemModel extends ViewModel {
    private final MutableLiveData<String[]> selectedItem = new MutableLiveData<String[]>();

    public void setData(String[] item){
        selectedItem.setValue(item);
    }
    public LiveData<String[]> getSelectedItem(){
        return selectedItem;
    }
}
