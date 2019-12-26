package com.example.cartoon.ui.cartoonLoader;

import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.cartoon.BaseActivity;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Util.CartoonMarkUtil;
import com.example.cartoon.model.Util.Const;
import com.example.cartoon.model.Util.JsoupUtil;
import com.example.cartoon.model.Util.LogUtil;
import com.example.cartoon.model.Util.StringUtils;
import com.example.cartoon.R;
import com.example.cartoon.widget.BatteryView;

import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_VOLUME_UP;


public class ImageViewActivity extends BaseActivity {
    private ViewPager viewPager;
    private PageLoaderAdapter adapter;
    private BatteryView batteryView;
    private TextView pagenum;
    private TextView chapter;
    private TextView powernum;
    private TextView time;

    private JsoupUtil jsoupUtil;
    private Cartoon cartoon;
    private int lastRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        initView();
        initDate();
    }

    private void initView() {
        chapter = findViewById(R.id.chapter);
        pagenum = findViewById(R.id.pagenum);
        batteryView = findViewById(R.id.battery);
        powernum = findViewById(R.id.power_num);
        time = findViewById(R.id.time_num);
        time.setText(StringUtils.dateConvert(System.currentTimeMillis(), "HH:mm"));
        viewPager = findViewById(R.id.main_page_view);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                chapter.setText("第"+(adapter.getItemData(position).getChapter()+1)+"话");
                pagenum.setText(adapter.getItemData(position).getPageNum()+"/"+adapter.getItemData(position).getIndex());
                if (lastRead > adapter.getItemData(position).getChapter()) {
                    LogUtil.Fanye("加载上一张");
                    lastRead = adapter.getItemData(position).getChapter();
                    loadPre(lastRead - 1);
                } else if (lastRead < adapter.getItemData(position).getChapter()) {
                    LogUtil.Fanye("加载下一张");
                    lastRead = adapter.getItemData(position).getChapter();
                    loadNext(lastRead + 1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, intentFilter);
    }

    private void initDate() {
        jsoupUtil = JsoupUtil.getsingleton();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Const.INTENT);
        cartoon = bundle.getParcelable(Const.CARTOON);
        assert cartoon != null;
        lastRead = CartoonMarkUtil.getMark(cartoon.getTitle(),cartoon.getType());
        cartoon.setCartoonNum(new SparseArray<ArrayList<String>>());
        jsoupUtil.cartoonImg(cartoon, lastRead, new JsoupUtil.ImgCallback() {
            @Override
            public void success(ArrayList<String> img) {
                cartoon.getCartoonNum().append(lastRead, img);
                final List<PageEntity> entities = new ArrayList<>();
                for (int i = 0; i < img.size(); i++) {
                    entities.add(new PageEntity(lastRead, i + 1, img.get(i),img.size()));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new PageLoaderAdapter(getSupportFragmentManager(), entities);
                        viewPager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        viewPager.setCurrentItem(1,false);
                        viewPager.setCurrentItem(0,false);
                        loadNext(lastRead + 1);
                        loadPre(lastRead - 1);
                    }
                });
            }
        });
    }

    private void loadPre(final int num) {
        if (!hasNext(num)) {
            adapter.addPreData(null);
            return;
        }
        if (cartoon.getCartoonNum().get(num) != null) {
            List<PageEntity> entities = new ArrayList<>();
            List<String> img = cartoon.getCartoonNum().get(num);
            for (int i = 0; i < img.size(); i++) {
                entities.add(new PageEntity(num, i + 1, img.get(i),img.size()));
            }
            adapter.addPreData(entities);
        } else {
            jsoupUtil.cartoonImg(cartoon, num, new JsoupUtil.ImgCallback() {
                @Override
                public void success(ArrayList<String> img) {
                    cartoon.getCartoonNum().append(num, img);
                    final List<PageEntity> entities = new ArrayList<>();
                    for (int i = 0; i < img.size(); i++) {
                        entities.add(new PageEntity(num, i + 1, img.get(i),img.size()));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addPreData(entities);
                        }
                    });
                }
            });
        }
    }

    private void loadNext(final int num) {
        if (!hasNext(num)) {
            adapter.addNextData(null);
            return;
        }
        if (cartoon.getCartoonNum().get(num) != null) {
            List<PageEntity> entities = new ArrayList<>();
            List<String> img = cartoon.getCartoonNum().get(num);
            for (int i = 0; i < img.size(); i++) {
                entities.add(new PageEntity(num, i + 1, img.get(i),img.size()));
            }
            adapter.addNextData(entities);
        } else {
            jsoupUtil.cartoonImg(cartoon, num, new JsoupUtil.ImgCallback() {
                @Override
                public void success(ArrayList<String> img) {
                    cartoon.getCartoonNum().append(num, img);
                    final List<PageEntity> entities = new ArrayList<>();
                    for (int i = 0; i < img.size(); i++) {
                        entities.add(new PageEntity(num, i + 1, img.get(i),img.size()));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addNextData(entities);
                        }
                    });
                }
            });
        }
    }

    private boolean hasNext(int num) {
        return num < cartoon.getCatalogsTitle().size() && num >= 0;
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                batteryView.setPower(level);
                powernum.setText(level+"%");
            } else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                time.setText(StringUtils.dateConvert(System.currentTimeMillis(), "HH:mm"));
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
            case KEYCODE_VOLUME_UP:
                return true;
            default:
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,false);
                return true;
            case KEYCODE_VOLUME_UP:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1,false);
                return true;
            default:
        }
        return  super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
