package com.example.cartoon.ui.cartoonLoader;

import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cartoon.BaseActivity;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Util.CartoonMarkUtil;
import com.example.cartoon.model.Util.Const;
import com.example.cartoon.model.Util.JsoupUtil;
import com.example.cartoon.model.Util.LogUtil;
import com.example.cartoon.model.Util.ScreenUtils;
import com.example.cartoon.model.Util.StringUtils;
import com.example.cartoon.R;
import com.example.cartoon.model.Util.notchtools.NotchTools;
import com.example.cartoon.model.Util.notchtools.core.NotchProperty;
import com.example.cartoon.model.Util.notchtools.core.OnNotchCallBack;
import com.example.cartoon.ui.catlog.CatlogActivity;
import com.example.cartoon.widget.BatteryView;
import com.example.cartoon.widget.ImageViewPager;
import com.example.cartoon.widget.ShowAndHideView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.view.KeyEvent.KEYCODE_VOLUME_UP;


public class ImageViewActivity extends BaseActivity {
    private ImageViewPager viewPager;
    private PageLoaderAdapter adapter;
    private BatteryView batteryView;
    private TextView pagenum;
    private TextView chapter;
    private TextView powernum;
    private TextView time;
    private ImageView image_view_back;
    private ImageView image_view_catLog;
    private ShowAndHideView top;
    private ShowAndHideView bottom;
    private JsoupUtil jsoupUtil;
    private Cartoon cartoon;
    private int lastRead;
    private int width = 0;
    private boolean menuShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        initView();
        Intent intent = getIntent();
        initDate(intent);
        initClick();
    }

    private void initClick() {
        image_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        image_view_catLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageViewActivity.this, CatlogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(CatlogActivity.CHOSECARTOON,cartoon);
                intent.putExtra(CatlogActivity.CHOSECARTOON,bundle);
                startActivity(intent);
            }
        });



        top.post(new Runnable() {
            @Override
            public void run() {
                NotchTools.getFullScreenTools().fullScreenUseStatus(ImageViewActivity.this, new OnNotchCallBack() {
                    @Override
                    public void onNotchPropertyCallback(final NotchProperty notchProperty) {
                        LogUtil.Log("刘海","长度为"+notchProperty.geNotchHeight());
                        if (notchProperty != null && notchProperty.isNotch()) {
                            top.post(new Runnable() {
                                @Override
                                public void run() {
                                    width = ScreenUtils.getScreenSize(ImageViewActivity.this)[0];
                                    top.setPadding(0, notchProperty.getMarginTop(), 0, 0);
                                    viewPager.setTopHeight(top.getHeight());
                                    viewPager.setButoom((int) bottom.getY());
                                    viewPager.setWeight(width);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        chapter = findViewById(R.id.chapter);
        pagenum = findViewById(R.id.pagenum);
        batteryView = findViewById(R.id.battery);
        powernum = findViewById(R.id.power_num);
        time = findViewById(R.id.time_num);
        image_view_back = findViewById(R.id.image_view_back);
        image_view_catLog = findViewById(R.id.image_view_catLog);
        top = findViewById(R.id.image_view_top);
        bottom = findViewById(R.id.image_view_bottom);

        viewPager = findViewById(R.id.main_page_view);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                chapter.setText("第" + (adapter.getItemData(position).getChapter() + 1) + "话");
                pagenum.setText(adapter.getItemData(position).getPageNum() + "/" + adapter.getItemData(position).getIndex());
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

    private void initDate(Intent intent) {
        menuShow = false;
        top.setVisibility(View.INVISIBLE);
        bottom.setVisibility(View.INVISIBLE);

        jsoupUtil = JsoupUtil.getsingleton();
        Bundle bundle = intent.getBundleExtra(Const.INTENT);
        cartoon = bundle.getParcelable(Const.CARTOON);
        assert cartoon != null;
        lastRead = CartoonMarkUtil.getMark(cartoon.getTitle(), cartoon.getType());
        cartoon.setCartoonNum(new SparseArray<ArrayList<String>>());
        jsoupUtil.cartoonImg(cartoon, lastRead, new JsoupUtil.ImgCallback() {
            @Override
            public void success(ArrayList<String> img) {
                cartoon.getCartoonNum().append(lastRead, img);
                final List<PageEntity> entities = new ArrayList<>();
                for (int i = 0; i < img.size(); i++) {
                    entities.add(new PageEntity(lastRead, i + 1, img.get(i), img.size()));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new PageLoaderAdapter(getSupportFragmentManager(), entities);
                        viewPager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        viewPager.setCurrentItem(1, false);
                        viewPager.setCurrentItem(0, false);
                        loadNext(lastRead + 1);
                        loadPre(lastRead - 1);
                    }
                });
            }
        });
        time.setText(StringUtils.dateConvert(System.currentTimeMillis(), "HH:mm"));
    }

    private void loadPre(final int num) {
        if (notNext(num)) {
            adapter.addPreData(null);
            return;
        }
        if (cartoon.getCartoonNum().get(num) != null) {
            List<PageEntity> entities = new ArrayList<>();
            List<String> img = cartoon.getCartoonNum().get(num);
            for (int i = 0; i < img.size(); i++) {
                entities.add(new PageEntity(num, i + 1, img.get(i), img.size()));
            }
            adapter.addPreData(entities);
        } else {
            jsoupUtil.cartoonImg(cartoon, num, new JsoupUtil.ImgCallback() {
                @Override
                public void success(ArrayList<String> img) {
                    cartoon.getCartoonNum().append(num, img);
                    final List<PageEntity> entities = new ArrayList<>();
                    for (int i = 0; i < img.size(); i++) {
                        entities.add(new PageEntity(num, i + 1, img.get(i), img.size()));
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
        if (notNext(num)) {
            adapter.addNextData(null);
            return;
        }
        if (cartoon.getCartoonNum().get(num) != null) {
            List<PageEntity> entities = new ArrayList<>();
            List<String> img = cartoon.getCartoonNum().get(num);
            for (int i = 0; i < img.size(); i++) {
                entities.add(new PageEntity(num, i + 1, img.get(i), img.size()));
            }
            adapter.addNextData(entities);
        } else {
            jsoupUtil.cartoonImg(cartoon, num, new JsoupUtil.ImgCallback() {
                @Override
                public void success(ArrayList<String> img) {
                    cartoon.getCartoonNum().append(num, img);
                    final List<PageEntity> entities = new ArrayList<>();
                    for (int i = 0; i < img.size(); i++) {
                        entities.add(new PageEntity(num, i + 1, img.get(i), img.size()));
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

    private boolean notNext(int num) {
        return num >= cartoon.getCatalogsTitle().size() || num < 0;
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                batteryView.setPower(level);
                powernum.setText(level + "%");
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
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, false);
                return true;
            case KEYCODE_VOLUME_UP:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, false);
                return true;
            default:
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null){
            initDate(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            if (menuShow){
                top.slideUpOut();
                bottom.slideDownOut();
                menuShow = false;
            }else {
                top.slideUpIn();
                bottom.slideDownIn();
                menuShow = true;
            }
            viewPager.setShow(menuShow);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
