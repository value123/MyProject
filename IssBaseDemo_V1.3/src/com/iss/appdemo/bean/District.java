package com.iss.appdemo.bean;

import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

import com.iss.bean.BaseBean;

public class District extends BaseBean<District> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String linkageid;
	public String name;
	public String order;
	public String parentid;
	public String hot;

	@Override
	public District parseJSON(JSONObject jsonObj) {
		linkageid = jsonObj.optString("linkageid");
		name = jsonObj.optString("name");
		order = jsonObj.optString("order");
		parentid = jsonObj.optString("parentid");
		hot = jsonObj.optString("hot");
		return this;
	}

	@Override
	public District cursorToBean(Cursor cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentValues beanToValues() {
		// TODO Auto-generated method stub
		return null;
	}


}
