package com.iss.example.imageloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.app.IssActivity;
import com.iss.appdemo.AppContext;
import com.iss.appdemo.R;
import com.iss.imageloader.core.DisplayImageOptions;
import com.iss.imageloader.core.ImageLoader;
import com.iss.imageloader.core.assist.PauseOnScrollListener;
import com.iss.imageloader.core.display.RoundedBitmapDisplayer;

public class ImageGridActivity extends IssActivity {
	private ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;
	private GridView mGridView;

	/**
	 * 控制AbsListView在onScroll时是否加载图片
	 */
	protected boolean pauseOnScroll = false;

	/**
	 * 控制AbsListView在onFling时是否加载图片
	 */
	protected boolean pauseOnFling = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_grid);
	}

	@Override
	protected void initView() {
		mGridView = (GridView) findViewById(R.id.gridview);
	}

	@Override
	protected void initData() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory()
				.cacheOnDisc()
				.decodingOptions(getOptions())
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 图片圆角处理，慎用，会增大内存使用
				.displayer(new RoundedBitmapDisplayer(30)).build();

		mGridView.setAdapter(new ImageAdapter());
	}

	@Override
	protected void setListener() {
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				startImagePagerActivity(position);
			}
		});

		mGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				pauseOnScroll, pauseOnFling, null));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_imageloader, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear_memory_cache:
			imageLoader.clearMemoryCache();
			return true;
		case R.id.item_clear_disc_cache:
			imageLoader.clearDiscCache();
			return true;
		default:
			return false;
		}
	}

	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra(AppContext.EXTRA_POSITION, position);
		startActivity(intent);
	}

	public Options getOptions() {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return opt;
	}

	public class ImageAdapter extends BaseAdapter {
		private class ViewHolder {
			public TextView text;
			public ImageView image;
		}
		
		@Override
		public int getCount() {
			return Images.imageThumbUrls.length;
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
			View view = convertView;
			final ViewHolder holder;
			final ImageView imageView;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.text);
				holder.image = (ImageView) view.findViewById(R.id.image);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.text.setText("Item " + (position + 1));
			imageLoader.displayImage(Images.imageThumbUrls[position],
					holder.image, options);

			return view;
		}
	}

}