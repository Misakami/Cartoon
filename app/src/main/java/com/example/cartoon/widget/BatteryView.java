package com.example.cartoon.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BatteryView extends View {

    private Paint mBatteryPait;
    private Paint mPowerPaint;
    private Paint myTextPaint;
    private float mBatteryStroke = 2.0f;
    private Context mycontext;
    /**
     * 屏幕的高宽
     *
     * @param context
     */
    private int measureWidth;
    private int measureHeight;

    /**
     * 电池参数
     *
     * @param context
     */
    private float mBatteryHeight = 30.0f;// 电池高度
    private float mBatteryWidth = 50.0f;// 电池的宽度
    private float mCapHeight = 15.0f;
    private float mCapWidth = 5.0f;


    /**
     * 电池电量
     *
     * @param context
     */
    private float mPowerPadding = 5.0f;
    private float mPowerHeight = mBatteryHeight - mBatteryStroke
            - mPowerPadding;
    private float mPowerWidth = mBatteryWidth - mBatteryStroke - mPowerPadding
            * 2;// 电池身体的总宽度
    private float mPower = 0.5f;
    private float padding = 10.0f;

    /**
     * 矩形
     */
    private RectF mBatteryRectF;
    private RectF mCapRectF;
    private RectF mPowerRectF;

    public BatteryView(Context context) {
        super(context);
        mycontext = context;
        initView();
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mycontext = context;
        initView();
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mycontext = context;
        initView();
    }

    private void initView() {
        /**
         * 设置电池画笔
         */
        mBatteryPait = new Paint();
        mBatteryPait.setColor(Color.WHITE);
        mBatteryPait.setStrokeWidth(mBatteryStroke);
        mBatteryPait.setStyle(Paint.Style.STROKE);
        mBatteryPait.setAntiAlias(true);
        /**
         * 电量画笔
         */
        mPowerPaint = new Paint();
        mPowerPaint.setColor(Color.RED);
        mPowerPaint.setStyle(Paint.Style.FILL);
        mPowerPaint.setStrokeWidth(mBatteryStroke);
        mPowerPaint.setAntiAlias(true);

        myTextPaint = new Paint();
        myTextPaint.setColor(Color.WHITE);
        myTextPaint.setStyle(Paint.Style.FILL);
        myTextPaint.setTextSize(30);
        myTextPaint.setAntiAlias(true);
        /**
         * 设置电池矩形
         */
        mBatteryRectF = new RectF(mCapWidth, padding, mCapWidth + mBatteryWidth,
                mBatteryHeight+padding);

        /**
         * 设置电池盖矩形
         */

        mCapRectF = new RectF(0, mBatteryHeight / 2 - mCapHeight / 2 + padding,
                mCapWidth, mBatteryHeight / 2 + mCapHeight / 2+padding);

        /**
         * 设置电量的矩形
         */

        mPowerRectF = new RectF(mBatteryWidth + mCapWidth - mPowerPadding
                - mPowerWidth*mPower, mPowerPadding + padding, mBatteryWidth + mCapWidth
                - mPowerPadding, mBatteryHeight - mPowerPadding+padding);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    public void setPower(int levle){
        mPower = (float) levle/100;
        postInvalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mPowerRectF.set(mBatteryWidth + mCapWidth - mPowerPadding
                - mPowerWidth*mPower, mPowerPadding + padding, mBatteryWidth + mCapWidth
                - mPowerPadding, mBatteryHeight - mPowerPadding+padding);
        canvas.drawRoundRect(mBatteryRectF, 2.0f, 2.0f, mBatteryPait);
        canvas.drawRoundRect(mCapRectF, 2.0f, 2.0f, mBatteryPait);
        canvas.drawRect(mPowerRectF, mPowerPaint);
        canvas.restore();
    }
}