package com.iss.appdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.iss.appdemo.R;
import com.iss.appdemo.bean.District;
import com.iss.view.wheel.AbstractWheelTextAdapter;

public class WheelDistrictAdapter extends AbstractWheelTextAdapter {
	private ArrayList<District> list;

	public WheelDistrictAdapter(Context context,List<District> areaList) {
		super(context,R.layout.layout_station_wheel_text);
		list = new ArrayList<District>();
		list.addAll(areaList);
		setItemTextResource(R.id.station_wheel_textView);
	}

	@Override
	public int getItemsCount() {
		return list.size()+1;
	}

	@Override
	protected CharSequence getItemText(int index) {
		if(index==0){
			return "不限";
		}else{
			return list.get(index-1).name;
		}
	}
	
	public District getBean(int position){
		if(position==0){
			District d = new District();
			d.name="不限";
			return  d;
		}
		if(position>0&&position<list.size()+1){
			return list.get(position-1);
		}
		return null;
	}

}
