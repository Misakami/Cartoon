package com.example.cartoon.model.CartoonWeb;

import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Util.JsoupUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManHuaNiu {
    /**
     * 漫画牛查找漫画
     * @param value
     * @param callback
     */
    public void searchCarton(String value, final JsoupUtil.Callback callback) {
        ArrayList<Cartoon> searchList = new ArrayList<>();
        try {
            String encode = URLEncoder.encode(value, "utf-8");
            Document document = Jsoup.connect("https://www.manhuaniu.com/search/?keywords=" + encode)
                    .get();
            Elements select = document.select("ul.book-list > li.item-lg");
            for (Element element : select) {
                Elements a = element.select("a");
                String href = a.attr("href");
                String title = a.attr("title");
                String src = a.select("img").attr("src");
                String text = a.select("span").text();
                Cartoon cartoon = new Cartoon(href,title,src,JsoupUtil.TypeManHuaNiu);
                cartoon.setLastUpdates(text);
                searchList.add(cartoon);
            }
            callback.success(searchList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchCatalog(Cartoon cartoon, final JsoupUtil.LogCallback callback) {
        ArrayList<String> catalogsUrl = new ArrayList<>();
        ArrayList<String> catalogsTitle = new ArrayList<>();
        try {
            Document document = Jsoup.connect(cartoon.getUrl())
                    .get();
            Elements select1 = document.select("div.comic-chapters");
            int i = select1.size()>2 ?select1.size() - 1 : 0;
            Element element = select1.get(i);
            Elements select = element.select("div.chapter-body > ul > li");
            for (Element e : select) {
                String attr = "https://www.manhuaniu.com" + e.select("a").attr("href");
                String text = e.text();
                catalogsUrl.add(attr);
                catalogsTitle.add(text);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cartoon.setCatalogsTitle(catalogsTitle);
        cartoon.setCatalogsUrl(catalogsUrl);
        callback.success(cartoon);
    }

    public void searchCartoonImg(Cartoon cartoon, int index, JsoupUtil.ImgCallback callback) {
        ArrayList<String> img = new ArrayList<>();
        try {
            Document document = Jsoup.connect(cartoon.getCatalogsUrl().get(index))
                    .get();
            Elements script = document.select("script");
            String text = "";
            for (Element element : script) {
                if (element.outerHtml().contains("pageImage")) {
                    text = element.outerHtml();
                    break;
                }
            }
            if (!"".equals(text)) {
                String pattern;
                pattern = "chapterImages = \\[(.*?)\\];";
                Pattern r = Pattern.compile(pattern);
                Matcher matcher = r.matcher(text);
                if (matcher.find()) {
                    String replace = matcher.group(1).replace("\\", "");
                    pattern = "\"(.*?)\"";
                    r = Pattern.compile(pattern);
                    matcher = r.matcher(replace);
                    while (matcher.find()) {
                       img.add ("https://res.nbhbzl.com//" + matcher.group(1));
                    }
                }
            }
            callback.success(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
