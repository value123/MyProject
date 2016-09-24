package com.example.immersivestatusbar.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.immersivestatusbar.R;

/**
 * Created by shenwenjie on 29/7/2016.
 */
public class ElasticTopBehavior extends CoordinatorLayout.Behavior<View>{

    private static final String TAG = "ElasticTopBehavior";

    public ElasticTopBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG,"layoutDependsOn >>>" + "child = " + child.getTag()+">>>dependency = " + dependency.getTag());
//       return super.layoutDependsOn(parent,child,dependency);//
        return dependency.getId() == R.id.first;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG,"onDependentViewChanged >>>" + "child = " + child.getTag()+">>>dependency = " + dependency.getTag());
        float marginStatusBar = dependency.getY()-child.getHeight();
        Log.d(TAG,"marginStatusBar = " + marginStatusBar);
        if(marginStatusBar>=0/*DisplayUtil.getStatusHeight(parent.getContext())*/) {
            child.setY(marginStatusBar);
        }
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG,"onNestedScroll >>>" + "child = " + child.getTag()+">>>target = " + target.getTag());
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

}
