package com.example.cartoon.model.Bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cartoonMark")
public class CartoonMark {
    @PrimaryKey(autoGenerate = true)
    private int markId;

    private String cartoonName;

    private int siteType;

    private int lastRead;

    public CartoonMark(String cartoonName, int siteType, int lastRead) {
        this.cartoonName = cartoonName;
        this.siteType = siteType;
        this.lastRead = lastRead;
    }

    public String getCartoonName() {
        return cartoonName;
    }

    public void setCartoonName(String cartoonName) {
        this.cartoonName = cartoonName;
    }

    public int getSiteType() {
        return siteType;
    }

    public void setSiteType(int siteType) {
        this.siteType = siteType;
    }

    public int getLastRead() {
        return lastRead;
    }

    public void setLastRead(int lastRead) {
        this.lastRead = lastRead;
    }

    public int getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }
}
