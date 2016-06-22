package com.jackson.demo;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
/**
 * Created by shenwenjie on 21/6/2016.
 */
public class PullRefreshLayout extends LinearLayout implements View.OnTouchListener{


    private Context context;
    private LayoutInflater inflater;
    private float yDown;
    private View headerLayout;
    private MarginLayoutParams headerLayoutLayoutParams;

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
        addView(headerLayout,0);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        View view = getChildAt(1);
        view.setOnTouchListener(this);
        headerLayoutLayoutParams = (MarginLayoutParams)(headerLayout.getLayoutParams());


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                yDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                float yMove = event.getRawY();
                int distance = (int) (yMove- yDown);
                headerLayoutLayoutParams.topMargin = (int)distance/2;
                headerLayout.setLayoutParams(headerLayoutLayoutParams);


                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
