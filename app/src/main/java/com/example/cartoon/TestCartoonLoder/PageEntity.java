package com.example.cartoon.TestCartoonLoder;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author misaka
 */
public class PageEntity implements Parcelable {
    /**
     * 章节
     */
    private int chapter;
    /**
     * 页数
     */
    private int pageNum;
    /**
     * 图片链接
     */
    private String imgSrc;

    /**
     * index
     */
    private int index;

    public PageEntity(int chapter, int pageNum, String imgSrc, int index) {
        this.chapter = chapter;
        this.pageNum = pageNum;
        this.imgSrc = imgSrc;
        this.index = index;
    }

    protected PageEntity(Parcel in) {
        chapter = in.readInt();
        pageNum = in.readInt();
        imgSrc = in.readString();
        index = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chapter);
        dest.writeInt(pageNum);
        dest.writeString(imgSrc);
        dest.writeInt(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PageEntity> CREATOR = new Creator<PageEntity>() {
        @Override
        public PageEntity createFromParcel(Parcel in) {
            return new PageEntity(in);
        }

        @Override
        public PageEntity[] newArray(int size) {
            return new PageEntity[size];
        }
    };

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "PageEntity{" +
                "chapter=" + chapter +
                ", pageNum=" + pageNum +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
