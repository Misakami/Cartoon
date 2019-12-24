package com.example.cartoon.Model.CartoonWeb;

import com.example.cartoon.Model.Bean.Cartoon;
import com.example.cartoon.Model.Util.JsoupUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManKeZhan {

    /**
     * 漫客栈查找漫画
     *
     * @param value
     * @param callback
     */
    public void searchCarton(String value, final JsoupUtil.Callback callback) {
        ArrayList<Cartoon> searchList = new ArrayList<>();
        try {
            Connection connect = Jsoup.connect("https://www.mkzhan.com/search/?keyword=" + value);
            Document document = connect.get();
            Elements elements = document.select("div.common-comic-item");
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                String href = element.select("a.cover").attr("href");
                String coverSrc = element.select("img").attr("data-src");
                String title = element.select("p.comic__title").text();
                String lastUpdates = element.select("p.comic-update").text();
                Cartoon cartoon = new Cartoon("https://www.mkzhan.com" + href, title, coverSrc,JsoupUtil.TypeMankeZhan);
                cartoon.setLastUpdates(lastUpdates);
                searchList.add(cartoon);
            }
            callback.success(searchList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 漫客栈查找当前点击漫画的目录
     * @param cartoon 当前的漫画
     */
    public void searchCatalog(Cartoon cartoon, final JsoupUtil.LogCallback callback) {
        ArrayList<String> catalogsUrl = new ArrayList<>();
        ArrayList<String> catalogsTitle = new ArrayList<>();
        try {
            Document document = Jsoup.connect(cartoon.getUrl()).get();
            Elements elements = document.select("a.j-chapter-link");
            String introduction = document.select("p.intro-total").text();
            String into = document.select("div.comic-status").text();
            cartoon.setIntroduction(into+"\n"+introduction);
            for (int i = 0; i < elements.size(); i++) {
                String url = elements.get(i).attr("data-hreflink");
                String title = elements.get(i).text();
                catalogsUrl.add("https://www.mkzhan.com" + url);
                catalogsTitle.add(title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(catalogsTitle);
        Collections.reverse(catalogsUrl);
        cartoon.setCatalogsTitle(catalogsTitle);
        cartoon.setCatalogsUrl(catalogsUrl);
        callback.success(cartoon);
    }


    /**
     * 找到当前目录的所有图片
     * @param cartoon
     * @param index
     * @param callback
     */
    public void searchCartoonImg(Cartoon cartoon, int index, JsoupUtil.ImgCallback callback) {
        ArrayList<String> immures = new ArrayList<>();
        try {
            Document document = Jsoup.connect(cartoon.getCatalogsUrl().get(index)).get();
            Elements elements = document.select("img.lazy-read");

            for (int i = 0; i < elements.size(); i++) {
                String href = elements.get(i).attr("data-src");
                immures.add(href);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        callback.success(immures);
    }

}
