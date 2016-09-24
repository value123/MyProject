package com.iss.appdemo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iss.appdemo.R;
import com.iss.appdemo.bean.Demo;

public class DemoAdapter extends BaseAdapter {
	private ArrayList<Demo> beanList;
	private Activity mContext;

	public DemoAdapter(Activity context) {
		beanList = new ArrayList<Demo>();
		mContext = context;
	}
	
	public void addItem(Demo bean,boolean clear){
		if(clear){
			beanList.clear();
		}
		beanList.add(bean);
		notifyDataSetChanged();
	}
	
	public void addItems(ArrayList<Demo> list,boolean clear){
		if(clear){
			beanList.clear();
		}
		beanList.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return beanList.size();
	}
	

	@Override
	public Demo getItem(int position) {
		return beanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = (TextView)LayoutInflater.from(mContext).inflate(R.layout.item_appdemo_text, parent,false);
		textView.setText(getItem(position).name);
		return textView;
	}
}
