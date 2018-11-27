package com.weisheng.chartdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MySumAllLayout extends RelativeLayout {

    private Paint txtPaint;
    private Paint rectPaint;

    public MySumAllLayout(Context context) {
        this(context, null);
    }

    public MySumAllLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MySumAllLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //

    }

    private float currX;
    private float currY;

    private View currTouchView = null;
    private boolean isInTouchView;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currX = event.getX();
        currY = event.getY();
        PointF touchP = new PointF(currX, currY);
        isInTouchView = false;
        //在这里判断，是否在某个view里面触摸的
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            float radius = view.getHeight() / 2f;
            PointF centerP = new PointF(view.getLeft() + view.getWidth() / 2f, view.getTop() +
                    view.getHeight() / 2f);
            if (isPointInCircle(touchP, centerP, radius)) {//就是这个view
                isInTouchView = true;
                if (currTouchView != view) {
                    //先把上一个view复原
                    if (currTouchView != null) {
                        currTouchView.animate().scaleX(1.0f).setDuration(50).start();
                        currTouchView.animate().scaleY(1.0f).setDuration(50).start();

                    }
                    //换了一个目标
                    view.animate().scaleX(1.2f).scaleX(1.1f).setDuration(150).start();
                    view.animate().scaleY(1.2f).scaleY(1.1f).setDuration(150).start();
                    letViewAbove(view);
                }
                currTouchView = view;
                break;
            }
        }
        invalidate();

        return true;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isInTouchView) {
            drawRound(canvas);
        }
    }

    private void drawRound(Canvas canvas) {
        float rectHeight = txtPaint.getFontSpacing() * 2 + 20;
        float rectWidth = txtPaint.measureText("销售总额") + 30;
        float leftX = currX;
        float leftY = currY;
        if (currY > getHeight()) {
            leftY = getHeight();
        }
        if (currY < 0f) {
            leftY = 0;
        }
        //超过边界改变x,y的值
        if (leftY + rectHeight > getHeight()) {
            leftY = leftY - rectHeight;
        }
        //round

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(leftX, leftY, leftX + rectWidth, leftY + rectHeight, 10, 10,
                    rectPaint);
        } else {
            canvas.drawRect(leftX, leftY, leftX + rectWidth, leftY + rectHeight, rectPaint);
        }
        ShowBean showBean = getShowBeanByView(currTouchView);
        if (showBean != null) {
            //txt
            canvas.drawText(showBean.title, leftX + 10, leftY + txtPaint.getFontSpacing(),
                    txtPaint);
            canvas.drawText(showBean.isDouble ? String.format("%.2f元", showBean.amount) :
                            (showBean.qty + "个"), leftX + 10, leftY + txtPaint.getFontSpacing() * 2,
                    txtPaint);
        }


    }

    @NonNull
    private Paint initPaint() {
        rectPaint = new Paint();
        rectPaint.setColor(Color.parseColor("#44000000"));

        txtPaint = new Paint();
        txtPaint.setColor(Color.WHITE);
        txtPaint.setTextSize(35);
        return txtPaint;
    }

    private boolean isPointInCircle(PointF pointF, PointF circle, float radius) {
        return Math.pow((pointF.x - circle.x), 2) + Math.pow((pointF.y - circle.y), 2) <= Math
                .pow(radius, 2);
    }

    //
    private void letViewAbove(View view) {
        List<View> views = new ArrayList<>();
        for (int i = getChildCount(); i > 0; i--) {
            if (getChildAt(i) == view) {
                views.add(0, view);
            } else {
                views.add(view);
            }
        }
//        removeAllViews();
        post(() -> {
            for (View v : views) {
                removeView(v);
                addView(v);
            }
        });
    }

    private List<ShowBean> mShowBeans;

    public void setListData(List<ShowBean> showBeans) {
        mShowBeans = showBeans;

    }

    public static class ShowBean {
        public int resId;
        public String title;
        public double amount;
        public int qty;
        public boolean isDouble;
    }

    private ShowBean getShowBeanByView(View view) {
        if (mShowBeans == null) {
            return null;
        }
        for (ShowBean mShowBean : mShowBeans) {
            if (mShowBean.resId == view.getId()) {
                return mShowBean;
            }
        }
        return null;
    }
}
