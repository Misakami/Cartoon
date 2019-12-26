package com.example.cartoon.model.Bean;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "netCache"
        ,foreignKeys = @ForeignKey(entity = NetCartoon.class, parentColumns = "ncId", childColumns = "ncCacheId",onDelete = CASCADE,onUpdate = CASCADE))
@TypeConverters(ContentsConverter.class)
public class NetCartoonCache {

    @PrimaryKey
    private int ncCacheId;

    private List<String> catalogsUrl;

    private List<String> catalogsTitle;

    public int getNcCacheId() {
        return ncCacheId;
    }

    public void setNcCacheId(int ncCacheId) {
        this.ncCacheId = ncCacheId;
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
