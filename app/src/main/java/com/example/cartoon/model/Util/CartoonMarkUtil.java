package com.example.cartoon.model.Util;

import com.example.cartoon.model.AppDateBase;
import com.example.cartoon.model.Bean.CartoonMark;
import com.example.cartoon.model.Bean.CartoonMarkDao;
import com.example.cartoon.model.Util.Thread.DefaultExecutorSupplier;

public class CartoonMarkUtil {
    public static void insertOrUpdateMark(final int position, final String name, final int type){
        DefaultExecutorSupplier.getInstance()
                .forLightWeightBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        CartoonMarkDao cartoonMarkDao = AppDateBase.getInstance()
                                .markDao();
                        CartoonMark select = cartoonMarkDao.select(name, type);
                        if (select != null){
                            select.setLastRead(position);
                            cartoonMarkDao.insert(select);
                        }else {
                            cartoonMarkDao.insert(new CartoonMark(name,type,position));
                        }
                    }
                });
    }

    public static int getMark(String name,int type){
        CartoonMarkDao cartoonMarkDao = AppDateBase.getInstance()
                .markDao();
        CartoonMark select = cartoonMarkDao.select(name, type);
        if (select == null){
            return 0;
        }
        return select.getLastRead();
    }
}
