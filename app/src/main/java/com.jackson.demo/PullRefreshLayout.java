package com.jackson.demo;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.lib.utils.AsynTaskExcutorUtil;

/**
 * Created by shenwenjie on 21/6/2016.
 */
public class PullRefreshLayout extends LinearLayout {


    private static final String TAG = "PullRefreshLayout";
    private Context context;
    private LayoutInflater inflater;
    private float yDown;
    private View headerLayout;
    private MarginLayoutParams headerLayoutLayoutParams;
    private boolean isFirstInit = true;
    private View mTarget;
    private boolean isBeingDraged= false;

    public PullRefreshLayout(Context context) {
        this(context, null);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttr(context);
    }

    private void initAttr(Context context) {

        inflater = LayoutInflater.from(context);
        headerLayout = inflater.inflate(R.layout.header_layout, null, false);
        setOrientation(VERTICAL);
        addView(headerLayout, 0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mTarget = getChildAt(1);
        if (isFirstInit) {
            isFirstInit = !isFirstInit;
            headerLayoutLayoutParams = (MarginLayoutParams) (headerLayout.getLayoutParams());
            headerLayoutLayoutParams.topMargin = -headerLayout.getMeasuredHeight();
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent");

        if (canChildScrollUp()) {
            return false;
        }

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isBeingDraged = false;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return isBeingDraged;
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent");
        //这里不能直接返回true或者false,因为内部的实现是
        return super.dispatchTouchEvent(ev);
    }

   /* @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG,"onTouch");

        return true;
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float yMove = event.getRawY();
                int distance = (int) (yMove - yDown);

                headerLayoutLayoutParams.topMargin = -headerLayout.getHeight() + distance / 2;
                headerLayout.setLayoutParams(headerLayoutLayoutParams);
                Log.d(TAG, "ACTION_MOVE >> topMargin=" + headerLayoutLayoutParams.topMargin);

                break;
            case MotionEvent.ACTION_UP:
                int topMargin = headerLayoutLayoutParams.topMargin;
                Log.d(TAG, "ACTION_UP >> topMargin=" + topMargin);
                if (topMargin >= 0) {
                    startRefresh();
                } else {
                    moveToStart();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void moveToStart() {
        stopRefresh();
    }

    private void startRefresh() {
        headerLayoutLayoutParams.topMargin = 0;
        headerLayout.setLayoutParams(headerLayoutLayoutParams);
        if (null != onRefreshListener) {
            RefreshTask refreshTask = new RefreshTask();
            AsynTaskExcutorUtil.execute(refreshTask);
            /*AsynTaskExcutorUtil.executeRunnable(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"onRefresh");
                    onRefreshListener.onRefresh();
                }
            });*/
        }
    }

    public OnRefreshListener onRefreshListener;

    public interface OnRefreshListener {
        void onRefresh();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }


    public void stopRefresh() {
        headerLayoutLayoutParams.topMargin = -headerLayout.getHeight();
        headerLayout.setLayoutParams(headerLayoutLayoutParams);
    }

    public class RefreshTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            onRefreshListener.onRefresh();
            super.onPostExecute(aVoid);
        }
    }
}
