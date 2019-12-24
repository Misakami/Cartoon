package com.example.cartoon.cartoonLoader;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.cartoon.model.Util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class PageLoaderAdapter extends OpenFragmentStatePagerAdapter<PageEntity> {

    private List<PageEntity> entities;
    private List<PageEntity> preEntities;
    private List<PageEntity> nowEntities = new ArrayList<>();
    private List<PageEntity> nexEntities;

    public PageLoaderAdapter(FragmentManager fm, List<PageEntity> entities) {
        super(fm);
        this.entities = entities;
        nowEntities.addAll(entities);
    }

    @Override
    public Fragment getItem(int position) {
        return CartoonLoaderFragment.getInstance(entities.get(position));
    }

    @Override
    PageEntity getItemData(int position) {
        return position >= entities.size()? null : entities.get(position);
    }

    @Override
    boolean dataEquals(PageEntity oldData, PageEntity newData) {
        return oldData.getImgSrc().equals(newData.getImgSrc());
    }

    @Override
    int getDataPosition(PageEntity data) {
        return data == null ? -1 : entities.indexOf(data);
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    /**
     * 加载下一章图片
     */
    public void addNextData(List<PageEntity> entity) {
        //初始化时统一处理
        if (nexEntities == null){
            nexEntities = new ArrayList<>();
            if (entity != null){
                nexEntities.addAll(entity);
            }else {
                return;
            }
        }else {
            //初始化过说明是点击到最后一个章节了
            if (entity == null){
                entities.removeAll(preEntities);
                preEntities.clear();
                preEntities.addAll(nowEntities);
                nowEntities.clear();
                nowEntities.addAll(nexEntities);
                nexEntities.clear();
                LogUtil.Fanye(entities.toString());
                notifyDataSetChanged();
                return;
            }else {
                entities.removeAll(preEntities);
                preEntities.clear();
                preEntities.addAll(nowEntities);
                nowEntities.clear();
                nowEntities.addAll(nexEntities);
                nexEntities.clear();
                nexEntities.addAll(entity);
            }
        }
        entities.addAll(entity);
        notifyDataSetChanged();
    }

    /**
     * 加载上一章图片
     */
    public void addPreData(List<PageEntity> entity) {
        //初始化时不管是不是第一章都按照pre处理
        if (preEntities == null) {
            preEntities = new ArrayList<>();
            if (entity != null) {
                preEntities.addAll(entity);
            }else {
                return;
            }
        }else {
            //不是第一次初始化传入null就说明时从后面加载到第一章了
            if (entity == null) {
                entities.removeAll(nexEntities);
                nexEntities.clear();
                nexEntities.addAll(nowEntities);
                nowEntities.clear();
                nowEntities.addAll(preEntities);
                preEntities.clear();
                notifyDataSetChanged();
                return;
            }else {
                entities.removeAll(nexEntities);
                nexEntities.clear();
                nexEntities.addAll(nowEntities);
                nowEntities.clear();
                nowEntities.addAll(preEntities);
                preEntities.clear();
                preEntities.addAll(entity);
            }
        }
        //如果不是空的就直接加到最前面
        entities.addAll(0, entity);
        notifyDataSetChanged();
    }


    /**
     * 添加最后一页
     */
    public void addLastData(PageEntity entity) {
        entities.add(entity);
        notifyDataSetChanged();
    }

    /**
     * 添加开头一页
     */
    public void addFirstData(PageEntity entity) {
        entities.add(0, entity);
        notifyDataSetChanged();
    }
}
