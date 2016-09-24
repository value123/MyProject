package com.iss.appdemo.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.iss.app.AbsDialog;
import com.iss.appdemo.R;


public class UserDialog extends AbsDialog implements View.OnClickListener {

	private Button button_edit;
	private Button button_delete;

	public UserDialog(Activity context) {
		super(context, R.style.dialog_menu);
		setContentView(R.layout.dialog_user_menu);
		setProperty(1,1);
	}
	
	@Override
	protected void initView() {
	    button_edit = (Button) findViewById(R.id.button_edit);
	    button_delete = (Button) findViewById(R.id.button_delete);
	}
	
	@Override
	protected void initData() {

	}
	
	@Override
	protected void setListener() {
	    button_edit.setOnClickListener(this);
	    button_delete.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
	    switch(v.getId()){
	        case R.id.button_edit:
	            if(mAction!=null){
	                mAction.editAction();
	            }
	            dismiss();
	            break;
	        case R.id.button_delete:
	            if(mAction!=null){
	                mAction.deleteAction();
	            }
	            dismiss();
	            break;
	    }

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
		public void editAction();
		public void deleteAction();
	}

}
