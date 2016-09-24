
package com.iss.example.view.common;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.iss.app.IssActivity;
import com.iss.appdemo.R;
import com.iss.view.common.ToastAlone;

public class CustomScrollViewActivity extends IssActivity {
    private Gallery mGallery;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customscrollview);
    }

    @Override
    protected void initView() {
        mGallery = (Gallery) findViewById(R.id.galley_pics);
        mViewPager = (ViewPager) findViewById(R.id.viewPager_pics);
    }

    @Override
    protected void initData() {
        mGallery.setAdapter(new GalleryAdapter());
        mViewPager.setAdapter(new ViewPagerAdapter());
    }

    @Override
    protected void setListener() {
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CustomScrollViewActivity.this, "使用系统Toast：" + position,
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    class GalleryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(CustomScrollViewActivity.this);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.loading_bg_200_300);
            return imageView;
        }

    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(CustomScrollViewActivity.this);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.loading_bg_200_300);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ToastAlone.showToast(CustomScrollViewActivity.this, "使用ToastAlone:" + position,
                            Toast.LENGTH_SHORT);
                }
            });
            container.addView(imageView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}
