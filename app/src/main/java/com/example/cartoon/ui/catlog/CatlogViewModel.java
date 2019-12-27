package com.example.cartoon.ui.catlog;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cartoon.model.AppDateBase;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Bean.NetCartoon;
import com.example.cartoon.model.Bean.NetCartoonCache;
import com.example.cartoon.model.Util.JsoupUtil;
import com.example.cartoon.model.Util.LogUtil;
import com.example.cartoon.model.Util.Thread.DefaultExecutorSupplier;
import java.util.Objects;

public class CatlogViewModel extends ViewModel {
    MutableLiveData<Cartoon> cartoon = new MutableLiveData<>();
    private MutableLiveData<NetCartoonCache> cache = new MutableLiveData<>();
    MutableLiveData<String> toast = new MutableLiveData<>();

    void getCartoons(final Cartoon cartoons) {
        DefaultExecutorSupplier.getInstance()
                .forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        NetCartoon netCartoon = AppDateBase.getInstance()
                                .cartoonDao()
                                .getNetCartoon(cartoons.getType(),cartoons.getTitle());
                        NetCartoonCache netCartoonCache = AppDateBase.getInstance()
                                .cacheDao()
                                .getcatLog(cartoons.getTitle(), cartoons.getType());
                        if (netCartoonCache != null && netCartoon != null){
                            LogUtil.Log("cache",netCartoonCache.getCatalogsTitle().size()+" "+netCartoonCache.getCatalogsUrl());
                            cache.postValue(netCartoonCache);
                            cartoon.postValue(new Cartoon(netCartoon,netCartoonCache));
                        }else {
                            JsoupUtil.getsingleton().catalog(cartoons, new JsoupUtil.LogCallback() {
                                @Override
                                public void success(Cartoon cartoons) {
                                    cartoon.postValue(cartoons);
                                    cache.postValue(cartoons.getCache());
                                }
                            });
                        }
                    }
                });
    }
    void joinCartoons(){
        if (cache.getValue() == null){
            toast.postValue("尚未解析目录成功请等待");
        }else {
            DefaultExecutorSupplier.getInstance()
                    .forLightWeightBackgroundTasks()
                    .execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.Log("数据库","插入数据库");
                    Cartoon cartoons = cartoon.getValue();
                    assert cartoons != null;
                    NetCartoon netCartoon = AppDateBase.getInstance()
                            .cartoonDao()
                            .getNetCartoon(cartoons.getType(), cartoons.getTitle());
                    if (netCartoon != null){
                        toast.postValue("已经在书架中了");
                        return;
                    }
                    AppDateBase.getInstance()
                            .cartoonDao()
                            .insert(Objects.requireNonNull(cartoon.getValue()).getNetCartoon());
                    AppDateBase.getInstance()
                            .cacheDao()
                            .insert(cache.getValue());
                    toast.postValue("加入成功");
                }
            });
        }
    }

    public void refersh() {

    }
}
