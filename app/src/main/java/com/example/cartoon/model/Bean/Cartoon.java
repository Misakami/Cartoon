package com.example.cartoon.model.Bean;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class Cartoon implements Parcelable {
    private String url;
    private String title;
    private String coverSrc;
    private int type;
    private String lastUpdates ="";
    private String introduction="";
    private ArrayList<String> catalogsUrl;
    private ArrayList<String> catalogsTitle;
    private SparseArray<ArrayList<String>> cartoonNum;

    public Cartoon() {
        catalogsTitle = new ArrayList<>();
        catalogsUrl = new ArrayList<>();
    }

    public Cartoon(Cartoon cartoons, NetCartoonCache cache) {
        this.url = cartoons.getUrl();
        this.title = cartoons.getTitle();
        this.coverSrc = cartoons.getCoverSrc();
        this.type = cartoons.getType();
        this.introduction = cartoons.getIntroduction();
        this.lastUpdates = cartoons.getLastUpdates();
        catalogsTitle = (ArrayList<String>) cache.getCatalogsTitle();
        catalogsUrl = (ArrayList<String>) cache.getCatalogsUrl();
        cartoonNum = new SparseArray<>();
    }

    public NetCartoon getNetCartoon(){
        return new NetCartoon(this);
    }
    public NetCartoonCache getCache(){
        return new NetCartoonCache(title,type,catalogsUrl,catalogsTitle);
    }

    public Cartoon(String url, String title, String coverSrc, int type) {
        this.url = url;
        this.title = title;
        this.coverSrc = coverSrc;
        this.type = type;
        catalogsTitle = new ArrayList<>();
        catalogsUrl = new ArrayList<>();
        cartoonNum = new SparseArray<>();
    }

    public Cartoon(NetCartoon cartoon,NetCartoonCache cache) {
        this.url = cartoon.getUrl();
        this.title = cartoon.getCartoonName();
        this.coverSrc = cartoon.getCoverSrc();
        this.type = cartoon.getSiteType();
        this.lastUpdates = cartoon.getLastUpdates();
        this.introduction = cartoon.getIntroduction();
        catalogsTitle = (ArrayList<String>) cache.getCatalogsTitle();
        catalogsUrl = (ArrayList<String>) cache.getCatalogsUrl();
        cartoonNum = new SparseArray<>();
    }

    public Cartoon(NetCartoon cartoon) {
        this.url = cartoon.getUrl();
        this.title = cartoon.getCartoonName();
        this.coverSrc = cartoon.getCoverSrc();
        this.type = cartoon.getSiteType();
        this.lastUpdates = cartoon.getLastUpdates();
        this.introduction = cartoon.getIntroduction();
        catalogsTitle = new ArrayList<>();
        catalogsUrl =new ArrayList<>();
        cartoonNum = new SparseArray<>();
    }


    protected Cartoon(Parcel in) {
        url = in.readString();
        title = in.readString();
        coverSrc = in.readString();
        type = in.readInt();
        lastUpdates = in.readString();
        introduction = in.readString();
        catalogsUrl = in.createStringArrayList();
        catalogsTitle = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(coverSrc);
        dest.writeInt(type);
        dest.writeString(lastUpdates);
        dest.writeString(introduction);
        dest.writeStringList(catalogsUrl);
        dest.writeStringList(catalogsTitle);
    }

    public static final Creator<Cartoon> CREATOR = new Creator<Cartoon>() {
        @Override
        public Cartoon createFromParcel(Parcel in) {
            return new Cartoon(in);
        }

        @Override
        public Cartoon[] newArray(int size) {
            return new Cartoon[size];
        }
    };

    public String getCoverSrc() {
        return coverSrc;
    }

    public void setCoverSrc(String coverSrc) {
        this.coverSrc = coverSrc;
    }



    public ArrayList<String> getCatalogsUrl() {
        return catalogsUrl;
    }

    public void setCatalogsUrl(ArrayList<String> catalogsUrl) {
        this.catalogsUrl = catalogsUrl;
    }

    public ArrayList<String> getCatalogsTitle() {
        return catalogsTitle;
    }

    public void setCatalogsTitle(ArrayList<String> catalogsTitle) {
        this.catalogsTitle = catalogsTitle;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public SparseArray<ArrayList<String>> getCartoonNum() {
        return cartoonNum;
    }

    public void setCartoonNum(SparseArray<ArrayList<String>> cartoonNum) {
        this.cartoonNum = cartoonNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }



}
