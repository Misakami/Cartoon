package com.example.cartoon.ui.homepage.bookshelf;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cartoon.model.AppDateBase;
import com.example.cartoon.model.Bean.NetCartoon;
import com.example.cartoon.model.Bean.NetCartoonCache;
import com.example.cartoon.model.Util.LogUtil;
import com.example.cartoon.model.Util.Thread.DefaultExecutorSupplier;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.Synchronized;

public class BookShelfViewModel extends ViewModel {
    private static final String TAG = "BookShelfViewModel";
    MutableLiveData<List<NetCartoon>> netCartoonUpdate = new MutableLiveData<>();
    MutableLiveData<List<NetCartoonCache>> netCartoonCache = new MutableLiveData<>();

    @Synchronized
    public void getUpdate(){

    }

    void click(final NetCartoon cartoon){
        DefaultExecutorSupplier.getInstance().forLightWeightBackgroundTasks().execute(new Runnable() {
            @Override
            public void run() {
                AppDateBase.getInstance().cartoonDao().upDate(cartoon);
            }
        });
    }

    void delectCartoon(final NetCartoon netCartoon){
        DefaultExecutorSupplier.getInstance()
                .forLightWeightBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDateBase.getInstance()
                                .cartoonDao()
                                .delect(netCartoon.getSiteType(),netCartoon.getCartoonName());
                    }
                });
    }

    @Synchronized
    void getDatebase(){
        LogUtil.Log("数据库","拉取书籍");
        DefaultExecutorSupplier.getInstance().forLightWeightBackgroundTasks().execute(new Runnable() {
            @Override
            public void run() {
                List<NetCartoon> netCartoons = AppDateBase.getInstance().cartoonDao().getNetCartoons();
                netCartoonUpdate.postValue(netCartoons);
                LogUtil.Log("数据库",netCartoons.size()+"");
                List<NetCartoonCache> cartoonCaches = new ArrayList<>();
                for (NetCartoon netCartoon:netCartoons) {
                    NetCartoonCache netCartoonCache = AppDateBase.getInstance().cacheDao().getcatLog(netCartoon.getCartoonName(),netCartoon.getSiteType());
                    cartoonCaches.add(netCartoonCache);
                }
                netCartoonCache.postValue(cartoonCaches);
            }
        });
    }
}
