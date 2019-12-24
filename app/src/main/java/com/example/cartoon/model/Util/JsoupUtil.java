package com.example.cartoon.model.Util;


import android.os.Build;

import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.CartoonWeb.ManHuaNiu;
import com.example.cartoon.model.CartoonWeb.ManKeZhan;
import com.example.cartoon.model.CartoonWeb.Misaka;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class JsoupUtil {
    /**
     * CartoonType 漫客栈
     */
    public static int TypeMankeZhan = 0;

    /**
     * 漫画牛
     */
    public static int TypeManHuaNiu = 1;
    /**
     *
     */
    public static int TypeMisaka = 2;


    private static volatile JsoupUtil jsoupUtil;
    private ManKeZhan manKeZhan;
    private Misaka misaka;
    private ManHuaNiu manHuaNiu;
    private ThreadPoolExecutor executor;

    private JsoupUtil() {
        misaka = new Misaka();
        manKeZhan = new ManKeZhan();
        manHuaNiu = new ManHuaNiu();
        executor = new ThreadPoolExecutor(2, 2, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public static JsoupUtil getsingleton() {
        if (jsoupUtil == null) {
            synchronized (JsoupUtil.class) {
                if (jsoupUtil == null) {
                    jsoupUtil = new JsoupUtil();
                }
            }
        }
        return jsoupUtil;
    }

    /**
     * 通过不同的网站查找漫画
     *
     * @param value 搜索值
     */
    public void search(final String value, final Callback callback) {
        if ("misaka".equals(value)) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    misaka.searchFuLi(callback);
                }
            });
        } else {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    manKeZhan.searchCarton(value, callback);
                }
            });
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    manHuaNiu.searchCarton(value, callback);
                }
            });
        }
    }

    /**
     * 查找当前点击漫画的目录
     *
     * @param cartoon 当前的漫画
     */
    public void catalog(final Cartoon cartoon, final LogCallback callback) {
        if (cartoon.getType() == TypeMisaka) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    misaka.searchFuLiCatlog(cartoon, callback);
                }
            });
        } else if (cartoon.getType() == TypeMankeZhan) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    manKeZhan.searchCatalog(cartoon, callback);
                }
            });
        } else if (cartoon.getType() == TypeManHuaNiu) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    manHuaNiu.searchCatalog(cartoon, callback);
                }
            });
        }
    }

    /**
     * 找到当前目录的所有图片
     *
     * @param cartoon  当前的漫画
     * @param index    当前的漫画的集数
     * @param callback 回调
     */
    public void cartoonImg(final Cartoon cartoon, final int index, final ImgCallback callback) {
        if (cartoon.getType() == TypeMisaka) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        misaka.searchFuLiImg(cartoon, index, callback);
                    }
                }
            });
        } else if (cartoon.getType() == TypeMankeZhan) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    manKeZhan.searchCartoonImg(cartoon, index, callback);
                }
            });
        } else if (cartoon.getType() == TypeManHuaNiu) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    manHuaNiu.searchCartoonImg(cartoon, index,callback);
                }
            });
        }
    }


    public interface Callback {
        void success(List<Cartoon> cartoons);
    }

    public interface LogCallback {
        void success(Cartoon cartoon);
    }

    public interface ImgCallback {
        void success(ArrayList<String> img);
    }
}
