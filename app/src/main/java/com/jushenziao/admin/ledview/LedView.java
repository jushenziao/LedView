package com.jushenziao.admin.ledview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class LedView extends ImageView {

    private static final int SHAPE_CIRCLE = 0;
    private static final int SHAPE_RECF = 1;
    private int mShape;

    private static final int RECTF_ROUND_RADIUS = 8;
    private static final int RECTF_ROUND_STRIKE_WIDTH = 2;

    public static int STATE_BUSYING = 0;//red
    public static int STATE_NO_REGISTERED = 2;//gray
    public static int STATE_REGISTERED = 1;//green
    public static int STATE_RINGING = 3;//flash
    private int mLampInitValue = 0;
    private int mCurrentState = STATE_NO_REGISTERED;
    private int[] mColorArr = new int[]{Color.RED, Color.GREEN, Color.GRAY};
    private int mCurrentColor = mColorArr[2];

    private int mStrikeWidth;
    private Paint mLedPaint;
    private Paint mBGPaint;
    private RectF mBGRectF;
	
    private RectF mLedRectF;

    public LedView(Context context) {
        this(context, null);
    }

    public LedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LedView);
        mShape = a.getInteger(R.styleable.LedView_led_shape, SHAPE_CIRCLE);
        initPanit(context);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        switch (mShape){
            case SHAPE_CIRCLE:
                setMeasuredDimension(60,60);
                break;
            case SHAPE_RECF:
                setMeasuredDimension(16,40);
                break;
        }
    }

    private void initPanit(Context context) {
        mLedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLedPaint.setAntiAlias(true);
        mStrikeWidth = DensityUtil.dip2px(context, 1);
        mBGPaint.setColor(Color.WHITE);
        mBGPaint.setAntiAlias(true);
        if (mShape == SHAPE_CIRCLE) {
            mBGPaint.setStyle(Paint.Style.STROKE);
            mBGPaint.setStrokeWidth(mStrikeWidth);
        }
    }

    private Runnable mRunnable = new Runnable() {

        public void run() {
            try {
                if (mCurrentState == STATE_RINGING) {
                    mLampInitValue = (mLampInitValue + 1) % 2;
                    mCurrentColor = mColorArr[mLampInitValue];
                    invalidate();
                    postDelayed(this, 500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBGRectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mLedRectF = new RectF(RECTF_ROUND_STRIKE_WIDTH, RECTF_ROUND_STRIKE_WIDTH, getMeasuredWidth() - RECTF_ROUND_STRIKE_WIDTH, getMeasuredHeight() - RECTF_ROUND_STRIKE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLedPaint.setColor(mCurrentColor);
        switch (mShape) {
            case SHAPE_CIRCLE:
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredWidth() / 2, getMeasuredWidth() / 2 - mStrikeWidth, mLedPaint);
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredWidth() / 2, getMeasuredWidth() / 2 - mStrikeWidth / 2, mBGPaint);
                break;
            case SHAPE_RECF:
                canvas.drawRoundRect(mBGRectF, RECTF_ROUND_RADIUS, RECTF_ROUND_RADIUS, mBGPaint);
                canvas.drawRoundRect(mLedRectF, RECTF_ROUND_RADIUS, RECTF_ROUND_RADIUS, mLedPaint);
        }
    }

    public synchronized void updateState(int state) {
        mCurrentState = state;
        if (state != STATE_RINGING) {
            mCurrentColor = mColorArr[mCurrentState];
            mLedPaint.setColor(mCurrentColor);
            invalidate();
        } else {
            post(mRunnable);
        }
    }

    public void resetState() {
        updateState(STATE_NO_REGISTERED);
    }

    public void removeCallbacks() {
        removeCallbacks(mRunnable);
    }
}
