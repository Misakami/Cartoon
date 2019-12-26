package com.example.cartoon.model.Bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 *  书架里的Cartoon
 */
@Entity(tableName = "netCartoon")
public class NetCartoon{
    @PrimaryKey(autoGenerate = true)
    private int ncId;

    private String cartoonName;

    private String url;

    private String siteName;

    /**
     *  最新章节名
     */
    private String lastUpdates ="未知";

    private String introduction="暂无";

    private String coverSrc;

    /**
     *  上次看的章节
     */
    private int lastRead = 0;
    /**
     * 上次点击书籍时的最新章节，判断是否显示有更新
     */
    private int lastReadLast;
    /**
     *  总章节数
     */
    private int catalogsSize;

    private long time = new Date().getTime();

    public int getNcId() {
        return ncId;
    }

    public void setNcId(int ncId) {
        this.ncId = ncId;
    }

    public String getCartoonName() {
        return cartoonName;
    }

    public void setCartoonName(String cartoonName) {
        this.cartoonName = cartoonName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLastUpdates() {
        return lastUpdates;
    }

    public void setLastUpdates(String lastUpdates) {
        this.lastUpdates = lastUpdates;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCoverSrc() {
        return coverSrc;
    }

    public void setCoverSrc(String coverSrc) {
        this.coverSrc = coverSrc;
    }

    public int getLastRead() {
        return lastRead;
    }

    public void setLastRead(int lastRead) {
        this.lastRead = lastRead;
    }

    public int getLastReadLast() {
        return lastReadLast;
    }

    public void setLastReadLast(int lastReadLast) {
        this.lastReadLast = lastReadLast;
    }

    public int getCatalogsSize() {
        return catalogsSize;
    }

    public void setCatalogsSize(int catalogsSize) {
        this.catalogsSize = catalogsSize;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
