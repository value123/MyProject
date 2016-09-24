package com.iss.appdemo.view;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.iss.app.AbsDialog;
import com.iss.appdemo.R;
import com.iss.appdemo.adapter.WheelDistrictAdapter;
import com.iss.appdemo.bean.District;
import com.iss.appdemo.bean.DistrictInfo;
import com.iss.view.wheel.OnWheelChangedListener;
import com.iss.view.wheel.WheelView;


public class ShopWheelViewDialog extends AbsDialog implements View.OnClickListener {
	private Activity mContext;

	private WheelView wheelView_view1;
	private WheelView wheelView_view2;
	private DistrictInfo districtInfo;
	private WheelDistrictAdapter adapter;
	private WheelDistrictAdapter adapter2;
	private Button button_ok;
	private Button button_cancel;

	public ShopWheelViewDialog(Activity context) {
		super(context, R.style.dialog_menu);
		mContext = context;
		setContentView(R.layout.dialog_shop_wheelview);
		setProperty(1,1);
	}
	
	@Override
	protected void initView() {
		wheelView_view1 = (WheelView) findViewById(R.id.wheelView_view1);
		wheelView_view2 = (WheelView) findViewById(R.id.wheelView_view2);
		button_cancel = (Button) findViewById(R.id.wheel_button_cancel);
		button_ok = (Button) findViewById(R.id.wheel_button_ok);
	}
	
	@Override
	protected void initData() {

	}
	
	@Override
	protected void setListener() {
		wheelView_view1.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (oldValue != newValue && districtInfo != null) {
					District areaBean = adapter.getBean(wheelView_view1.getCurrentItem());
					ArrayList<District> stationList = districtInfo.getChildDistricts(areaBean);
					adapter2 = new WheelDistrictAdapter(mContext, stationList);
					wheelView_view2.setViewAdapter(adapter2);
					wheelView_view2.setCurrentItem(0);
				}

			}
		});

		button_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mAction != null && districtInfo != null) {
					District bean1 = null;
					District bean2 = null;
					if(adapter!=null){
						bean1 = adapter.getBean(wheelView_view1.getCurrentItem());
					}
					if(adapter2!=null){
						bean2  = adapter2.getBean(wheelView_view2.getCurrentItem());
					}
					mAction.doAction(bean1, bean2);
				}
				dismiss();
			}
		});

		button_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setData(DistrictInfo bean) {
		districtInfo = bean;
		adapter = new WheelDistrictAdapter(mContext, districtInfo.getRootDistricts());
		wheelView_view1.setViewAdapter(adapter);
		District areaBean = adapter.getBean(wheelView_view1.getCurrentItem());
		ArrayList<District> stationList = districtInfo.getChildDistricts(areaBean);
		adapter2 = new WheelDistrictAdapter(mContext, stationList);
		wheelView_view2.setViewAdapter(adapter2);
		wheelView_view2.setCurrentItem(0);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void show() {
		super.show();
	}

	public void show(ConfirmAction action) {
		setConfirmAction(action);
		show();
	}

	private ConfirmAction mAction;

	public void setConfirmAction(ConfirmAction action) {
		mAction = action;

	}

	public interface ConfirmAction {
		public void doAction(District root, District child);
	}

}
