package com.iss.example.view.orientationviewpager;

import android.os.Bundle;
import android.support.v4.view.OrientationViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iss.appdemo.R;
import com.iss.appdemo.activity.EgActivity;

public class EgOrientationVP extends EgActivity{
    private Button button_horizontal;
    private Button button_vertical;
    private OrientationViewPager orientationViewPager;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientationvp);
    }

    @Override
    protected void initView() {
        button_horizontal = (Button)findViewById(R.id.button_horizontal);
        button_vertical = (Button)findViewById(R.id.button_vertical);
        orientationViewPager = (OrientationViewPager)findViewById(R.id.orientationViewPager);
    }

    @Override
    protected void initData() {
        orientationViewPager.setAdapter(new MyAdapter());
    }

    @Override
    protected void setListener() {
        button_horizontal.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                orientationViewPager.setOrientation(OrientationViewPager.HORIZONTAL);
            }
        });
        
        
        button_vertical.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                orientationViewPager.setOrientation(OrientationViewPager.VERTICAL);
            }
        });
    }
    
    
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(EgOrientationVP.this);
            textView.setTextSize(30);
            textView.setText(position+"");
            textView.setGravity(Gravity.CENTER);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((TextView)object);
        }
        
        
        
        
        
    }
    

}
