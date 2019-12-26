package com.example.cartoon.ui.homepage.bookshelf;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cartoon.model.AppDateBase;
import com.example.cartoon.model.Bean.NetCartoon;
import com.example.cartoon.model.Util.Thread.DefaultExecutorSupplier;

import java.util.List;

public class BookShelfViewModel extends ViewModel {
    private static final String TAG = "BookShelfViewModel";
    MutableLiveData netCartoonUpdate = new MutableLiveData<List<NetCartoon>>();

    public void getUpdate(){

    }

    public void getDatebase(){
        DefaultExecutorSupplier.getInstance().forLightWeightBackgroundTasks().execute(new Runnable() {
            @Override
            public void run() {
                List<NetCartoon> netCartoons = AppDateBase.getInstance().cartoonDao().getNetCartoons();
                netCartoonUpdate.postValue(netCartoons);
            }
        });
    }
}
