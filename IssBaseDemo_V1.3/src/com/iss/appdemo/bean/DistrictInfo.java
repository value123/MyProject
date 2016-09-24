package com.iss.appdemo.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

import com.iss.bean.BaseBean;

public class DistrictInfo extends BaseBean<DistrictInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int result;
	public int count;
	public ArrayList<District> list;

	@Override
	public DistrictInfo parseJSON(JSONObject jsonObj) {
		result = jsonObj.optInt("result");
		count = jsonObj.optInt("count");
		JSONArray array = jsonObj.optJSONArray("list");
		if (array != null) {
			list = new ArrayList<District>();
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject obj = array.optJSONObject(i);
				if (obj != null) {
					District bean = new District();
					list.add(bean.parseJSON(obj));
				}
			}

		}
		return this;
	}
	
	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DistrictInfo cursorToBean(Cursor cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentValues beanToValues() {
		// TODO Auto-generated method stub
		return null;
	}





	/**
	 * 用于商家界面获取下拉菜单的一级菜单数据
	 * 
	 * @return
	 */
	public ArrayList<District> getRootDistricts() {
		ArrayList<District> rootList = new ArrayList<District>();
		if (list != null && list.size() > 0) {
			for (District dis : list) {
				if (dis.parentid.equals("0")) {
					rootList.add(dis);
				}
			}
		}
		return rootList;
	}

	/**
	 * 用于商家界面获取下拉菜单的二级菜单数据
	 * 
	 * @return
	 */
	public ArrayList<District> getChildDistricts(District district) {
		ArrayList<District> childList = new ArrayList<District>();
		if (district != null && list != null && list.size() > 0) {
			for (District dis : list) {
				if (dis.parentid.equals(district.linkageid)) {
					childList.add(dis);
				}
			}
		}
		return childList;
	}

	

	

}
