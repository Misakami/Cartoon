package com.example.cartoon.model.CartoonWeb;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Util.JsoupUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Misaka {

    /**
     * 第一页的60篇漫画
     *
     * @param callback 成功的回调
     */
    public void searchFuLi(final JsoupUtil.Callback callback) {
        Document document = null;
        try {
            document = Jsoup.connect("http://www.66mh.cc/list/xiee/")
                    .get();
            Elements select = document.select("div#dmList > ul > li");
            System.out.println("第一页共" + select.size());
            ArrayList<Cartoon> searchList = new ArrayList<>();
            for (int i = 0; i < select.size(); i++) {
                Element element = select.get(i);
                Elements select1 = element.select("p > a.pic");
                String href = "http://www.66mh.cc/" + select1.attr("href");
                String covers = select1.select("img").attr("src");
                String title = select1.select("img").attr("alt");
                Elements select2 = element.select("dl > dd > p.intro");
                String intro = select2.text();
                System.out.println(title + ": " + href + "  " + covers + "  " + intro);
                Cartoon cartoon = new Cartoon(href, title, covers, JsoupUtil.TypeMisaka);
                cartoon.setIntroduction(intro);
                searchList.add(cartoon);
            }
            callback.success(searchList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchFuLiCatlog(Cartoon cartoon, final JsoupUtil.LogCallback callback) {
        ArrayList<String> catalogsUrl = new ArrayList<>();
        ArrayList<String> catalogsTitle = new ArrayList<>();
        Document document = null;
        try {
            document = Jsoup.connect(cartoon.getUrl())
                    .get();
            Elements select = document.select("div.w980_b1px > div.plist  > ul > li > a");
            for (int i = select.size() - 1; i >= 0; i--) {
                Element element = select.get(i);
                String href = "http://www.66mh.cc" + element.attr("href");
                String title = element.attr("title");
                catalogsUrl.add(href);
                catalogsTitle.add(title);
            }
            cartoon.setCatalogsTitle(catalogsTitle);
            cartoon.setCatalogsUrl(catalogsUrl);
            callback.success(cartoon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void searchFuLiImg(Cartoon cartoon, int index, JsoupUtil.ImgCallback callback){
        ArrayList<String> imgs = new ArrayList<>();
        Document document = null;
        try {
            document = Jsoup.connect(cartoon.getCatalogsUrl().get(index))
                    .get();
            Elements script = document.select("script");
            String text = "";
            for (int i = 0; i <script.size() ; i++) {
                Element element = script.get(i);
                if (element.outerHtml().contains("qTcms_S_m_murl_e")){
                    text = element.outerHtml();
                }
            }
            if (!text.equals("")){
                String pattern;
                pattern = "qTcms_S_m_murl_e=\"(.*)\"";
                Pattern r = Pattern.compile(pattern);
                Matcher matcher = r.matcher(text);
                if (matcher.find()){
                    jiemi(matcher.group(1),imgs);
                }
            }
            callback.success(imgs);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void jiemi(String encode, ArrayList<String> imgs) {
        byte[] decode = Base64.getDecoder().decode(encode);
        String qTcms_S_m_murl = null;
        try {
            qTcms_S_m_murl = new String(decode, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] out = qTcms_S_m_murl.split("\\$qingtiandy\\$");
        for (int i = 0; i < out.length; i++) {
            String imgsrc = "http://www.66mh.cc/" + out[i];
            imgs.add(imgsrc);
        }
    }

}
