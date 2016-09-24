
package com.iss.example.view.leftgallery;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.iss.app.IssActivity;
import com.iss.appdemo.R;
import com.iss.view.common.ToastAlone;

public class LeftGalleryActivity extends IssActivity {
    private com.iss.view.leftgallery.Gallery  gallery_left;
    private Gallery gallery_normal;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leftgallery);
    }

    @Override
    protected void initView() {
        gallery_left = (com.iss.view.leftgallery.Gallery)findViewById(R.id.gallery_left);
        gallery_normal = (Gallery) findViewById(R.id.gallery_normal);
    }

    @Override
    protected void initData() {
        gallery_left.setAdapter(new GalleryAdapter());
        gallery_normal.setAdapter(new GalleryAdapter());
    }

    @Override
    protected void setListener() {
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
            ImageView imageView = new ImageView(LeftGalleryActivity.this);
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
            ImageView imageView = new ImageView(LeftGalleryActivity.this);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.loading_bg_200_300);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ToastAlone.showToast(LeftGalleryActivity.this, "使用ToastAlone:" + position,
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
