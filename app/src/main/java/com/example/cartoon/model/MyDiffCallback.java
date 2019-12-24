package com.example.cartoon.model;

import androidx.recyclerview.widget.DiffUtil;

import com.example.cartoon.model.Bean.Cartoon;


public class MyDiffCallback extends DiffUtil.Callback {

    private Cartoon current;
    private Cartoon next;

    public MyDiffCallback(Cartoon current, Cartoon next) {
        this.current = current;
        this.next = next;
    }
    @Override
    public int getOldListSize() {
        return current.getCatalogsTitle().size();
    }

    @Override
    public int getNewListSize() {
        return next.getCatalogsTitle().size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return current.getCatalogsTitle().get(oldItemPosition).equals(next.getCatalogsTitle().get(newItemPosition))
                &&current.getCatalogsUrl().get(oldItemPosition).equals(next.getCatalogsUrl().get(newItemPosition));

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
