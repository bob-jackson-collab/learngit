package com.example.administrator.viewdragerhelper;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/4/8.
 */
public class DrayLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private MyCallback mCallback;
    private LinearLayout mMainLinear;   //主布局
    private LinearLayout mLeftLinear;   //左侧布局
    private int mHeight;
    private int mWidth;
    private int mRange; //左边布局拖拽的位置

    public DrayLayout(Context context) {
        super(context);
        init();
    }

    public DrayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrayLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mCallback = new MyCallback();
        mDragHelper = ViewDragHelper.create(this,1.0f,mCallback);
    }

    class MyCallback extends ViewDragHelper.Callback{


        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //返回true代表的是ViewGroup中的子控件可以随意拖动
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //如果当前布局是主布局
            if(child == mMainLinear){
                if(left<0){
                    return 0;  //主布局不能向左边拖拽
                }else if(left > mRange){
                    return mRange;
                }
            }
            //返回子控件拖动后左侧的位置
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //返回子控件拖动后顶部的位置
            return 0;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            int newLeft = left;

            if (changedView == mLeftLinear) {
            newLeft = mMainLinear.getLeft() + dx;
            }

            newLeft = fixLeft(newLeft);
            //当左边布局位置发生变化之后，拖拽左边布局也可以让主布局往回移动
            if(changedView == mLeftLinear){
                mLeftLinear.layout(0,0,mWidth,mHeight);
                mMainLinear.layout(newLeft, 0, newLeft + mWidth, 0 + mHeight);
            }

        }

        //当View被释放的时候需要处理的事件
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.e("---->",xvel + "......" + yvel);
            if(xvel>0 && mMainLinear.getLeft() > (mRange /2.0f)){
                open();
            }else{
                close();
            }
        }
    }

    //打开侧滑
    public void open(){
        int finalLeft = mRange;
        if(mDragHelper.smoothSlideViewTo(mMainLinear,finalLeft,0)){
            ViewCompat.postInvalidateOnAnimation(this);
        }else{

        }

    }
    //关闭侧滑
    public void close(){
        int finalLeft = 0 ;
        //1、触发动画
        if (mDragHelper.smoothSlideViewTo(mMainLinear, finalLeft, 0)) {
            //参数传this,也就是child所在的viewgroup
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //自己控件判断是否拦截事件
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //自己去处理触摸事件
        mDragHelper.processTouchEvent(event);
        return true;
    }

    //当xml填充完毕的时候才是调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //得到左边布局
        mLeftLinear = (LinearLayout) getChildAt(0);
        //得到主布局
        mMainLinear = (LinearLayout) getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        //控制昨天布局拖拽的位置
        mRange = (int) (mWidth * 0.7);
    }

    private int fixLeft(int left) {
        if (left < 0) {
            return 0;
        } else if (left > mRange) {
            return mRange;
        }
        return left;
    }
}
