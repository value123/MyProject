package com.iss.appdemo.bean;

public class Demo {

	public String name;
	public Class<?> s;
	
	public int[] pics;

	public Demo(String name, Class<?> s) {
		super();
		this.name = name;
		this.s = s;
	}
	
	public Demo setDrawable(int[] drawableIds){
		pics = drawableIds;
		return this;
	}
}
