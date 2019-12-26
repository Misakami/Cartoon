package com.example.cartoon.model.Bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import java.util.List;


@Entity(tableName = "netCache",primaryKeys = {"cartoonName","siteType"})
@TypeConverters(ContentsConverter.class)
public class NetCartoonCache {

    @NonNull
    private String cartoonName;

    @NonNull
    private int siteType;

    private List<String> catalogsUrl;

    private List<String> catalogsTitle;

    public NetCartoonCache() { }

    @Ignore
    public NetCartoonCache(String cartoonName, int siteType, List<String> catalogsUrl, List<String> catalogsTitle) {
        this.cartoonName = cartoonName;
        this.siteType = siteType;
        this.catalogsUrl = catalogsUrl;
        this.catalogsTitle = catalogsTitle;
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

    public List<String> getCatalogsUrl() {
        return catalogsUrl;
    }

    public void setCatalogsUrl(List<String> catalogsUrl) {
        this.catalogsUrl = catalogsUrl;
    }

    public List<String> getCatalogsTitle() {
        return catalogsTitle;
    }

    public void setCatalogsTitle(List<String> catalogsTitle) {
        this.catalogsTitle = catalogsTitle;
    }
}
