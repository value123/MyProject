package com.iss.appdemo.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.iss.app.IssActivity;
import com.iss.appdemo.AppContext;
import com.iss.example.view.photoview.HackyViewPager;
import com.iss.utils.ImageUtils;
import com.iss.view.photoview.PhotoView;

public class DemoDescImagesActivity extends IssActivity {

	private ViewPager mViewPager;
	private int[] drawableIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new HackyViewPager(this);
		setContentView(mViewPager);
	}

	@Override
	protected void initView() {
	}

	@Override
	protected void initData() {
		drawableIds = getIntent().getIntArrayExtra(
				AppContext.INTENT_DESC_IMAGES);
		if (drawableIds != null) {
			mViewPager.setAdapter(new SamplePagerAdapter());
		}
	}

	@Override
	protected void setListener() {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return drawableIds.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			// String imageUri = "drawable://"+drawableIds[position];
			// DisplayImageOptions options = new DisplayImageOptions.Builder()
			// .decodingOptions(getOptions())
			// .imageScaleType(ImageScaleType.NONE)
			// .build();
			//
			// ImageLoader.getInstance().displayImage(imageUri, photoView,
			// options);

			// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
			// drawableIds[position]);

			BitmapDrawable drawable = ImageUtils.readDrawable(container.getContext(),drawableIds[position]);
			photoView.setImageDrawable(drawable);
			
//			Bitmap bitmap = BitmapUtils.readBitMap(container.getContext(), drawableIds[position]);
//			photoView.setImageBitmap(bitmap);
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

//	
//
//	public static Options getOptions() {
//		BitmapFactory.Options opt = new BitmapFactory.Options();
//		opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
//		opt.inPurgeable = true;
//		opt.inInputShareable = true;
//		return opt;
//	}
}
