package com.example.immersivestatusbar.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.immersivestatusbar.R;
import com.lib.utils.DisplayUtil;

/**
 * Created by shenwenjie on 6/7/2016.
 */
public class MeRecycleDivider extends RecyclerView.ItemDecoration {

    private int mOrientation = LinearLayoutManager.VERTICAL;
    /**
     * item之间分割线的size，默认为1
     */
    private int mItemDividerSize;
    private Context mContext;
    private Paint mPaint;


    public MeRecycleDivider(Context context) {
        this(context, LinearLayoutManager.VERTICAL);
    }

    public MeRecycleDivider(Context context, int orientation) {
        this.mContext = context;
        if (mOrientation != LinearLayoutManager.VERTICAL && mOrientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("error orientation argument");
        }
        this.mOrientation = orientation;
        initPaint();
    }

    private void initPaint() {
        mItemDividerSize = (int) DisplayUtil.dip2px(mContext, 1f);//初始化为1dp

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mContext.getResources().getColor(R.color.common_divider));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontal(c, parent);
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mItemDividerSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mItemDividerSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 设置item分割线的size
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mItemDividerSize);
        } else {
            outRect.set(0, 0, mItemDividerSize, 0);
        }
    }

}
