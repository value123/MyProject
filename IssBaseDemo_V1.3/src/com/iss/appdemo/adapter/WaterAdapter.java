package com.iss.appdemo.adapter;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;

import com.iss.appdemo.R;
import com.iss.appdemo.view.WaterImageView;
import com.iss.view.waterfall.AbsWaterAdapter;

public class WaterAdapter extends AbsWaterAdapter {
	public ArrayList<String> urls;
	private Activity mContext;
	private final int COLUMNS = 3;
	public WaterAdapter(Activity activity){
		mContext = activity;
		urls = new ArrayList<String>();
	}
	
	public void addItem(String[] images,boolean clear){
		if(clear){
			urls.clear();
		}
		if(images!=null&&images.length>0){
			for(String url:images){
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
		if (url != null) {
			//通过获取随机数来控制图片显示的大小
			int imageType = getRandom();
			switch(position){
			//强制设置前8个view的随机数，目的是为了看到的第一屏瀑布流样式效果一样
			case 0:
				imageType = 2;
				break;
			case 1:
				imageType = 0;
				break;
			case 2:
				imageType = 1;
				break;
			case 3:
				imageType = 2;
				break;
			case 4:
				imageType = 1;
				break;
			case 5:
				imageType = 2;
				break;
			case 6:
				imageType = 2;
				break;
			case 7:
				imageType = 2;
				break;
			}
			switch (imageType) {
			//在这里设置的宽高，ItotemWaterView里会根据实际屏幕宽度来等比拉伸
			case 0:
				imageView.setDefault(R.drawable.loading_bg_192_144);
				imageView.setLayoutParams(new LayoutParams(200, 150));
				break;
			case 1:
				imageView.setDefault(R.drawable.loading_bg_192_192);
				imageView.setLayoutParams(new LayoutParams(200, 200));
				break;
			case 2:
				imageView.setDefault(R.drawable.loading_bg_200_300);
				imageView.setLayoutParams(new LayoutParams(200, 300));
				break;
			default:
				imageView.setDefault(R.drawable.loading_bg_192_192);
				imageView.setLayoutParams(new LayoutParams(200, 200));
				break;
			}
			if (!TextUtils.isEmpty(url)) {
				imageView.setUrl(url);
			}
		}
		return imageView;
	}
	
	/***
	 * 获取一个随机数
	 * @return
	 */
	private int getRandom() {
		Random random = new Random();
		int index = random.nextInt(1000)+random.nextInt(1000);//生成大于0小于1000的随机数
		return index/3;
	}

	
}
