package com.iss.appdemo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;

import com.iss.appdemo.R;
import com.iss.appdemo.view.WaterImageView;
import com.iss.view.waterfall.AbsWaterAdapter;

public class WaterAsListViewAdapter extends AbsWaterAdapter {
	public ArrayList<String> urls;
	private Activity mContext;
	private final int COLUMNS = 1;

	public WaterAsListViewAdapter(Activity activity) {
		mContext = activity;
		urls = new ArrayList<String>();
	}

	public void addItem(String[] images, boolean clear) {
		if (clear) {
			urls.clear();
		}
		if (images != null && images.length > 0) {
			for (String url : images) {
				urls.add(url);
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public int getColumns() {
		return COLUMNS;
	}

	@Override
	public int getCount() {
		return urls.size();
	}

	@Override
	public Object getItem(int position) {
		return urls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    WaterImageView imageView = null;
		if (convertView == null) {
			imageView = new WaterImageView(mContext);
		} else {
			imageView = (WaterImageView) convertView;
		}
		imageView.setScaleType(ScaleType.CENTER_CROP);
		String url = urls.get(position);
		if (!TextUtils.isEmpty(url)) {
			imageView.setDefault(R.drawable.loading_bg_192_144);
			//在这里设置的宽高，ItotemWaterView里会根据实际屏幕宽度来等比拉伸
			imageView.setLayoutParams(new LayoutParams(200, 150));
			imageView.setUrl(url);
		}
		return imageView;
	}

}
