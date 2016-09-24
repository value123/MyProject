
package com.iss.appdemo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iss.appdemo.R;
import com.iss.appdemo.bean.User;
import com.iss.appdemo.db.DBUtil;
import com.iss.utils.PhoneInfo;
import com.iss.view.common.ToastAlone;

public class UserInfoView extends LinearLayout {
    private TextView textView_name;

    private EditText editText_password;

    private Button button_enter;

    private User mUser;

    public UserInfoView(Context context) {
        super(context);
        initView(context);
        setListener();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_db_user, this);
        textView_name = (TextView) findViewById(R.id.textView_name);
        editText_password = (EditText) findViewById(R.id.editText_password);
        button_enter = (Button) findViewById(R.id.button_enter);
    }

    private void setListener() {
        
        button_enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View focusView = findFocus();
                if(focusView!=null){
                    PhoneInfo.hideSoftInputMode(getContext(), findFocus());
                }
                if (mUser != null) {
                    String butStr = button_enter.getText().toString();
                    if (butStr.equals("删除")) {
                        int result = DBUtil.deleteUser(getContext(), mUser);
                        if (result != 0) {
                            ToastAlone.showToast(getContext(), "删除成功", Toast.LENGTH_SHORT);
                        }
                    } else if (butStr.equals("修改")) {
                        String password = editText_password.getText().toString();
                        mUser.password = password;
                        int result = DBUtil.updateUser(getContext(), mUser);
                        if (result != 0) {
                            ToastAlone.showToast(getContext(), "修改成功", Toast.LENGTH_SHORT);
                        }
                    }
                }

            }
        });
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        // return false;
//        return super.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
//        // return super.onInterceptTouchEvent(ev);
//    }
//    
//    
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return false;
////        return super.onTouchEvent(event);
//    }

    public void setData(User user) {
        mUser = user;
        textView_name.setText(mUser.name);
        editText_password.setText(mUser.password);
        switch (mUser.infoState) {
            case normal:
                button_enter.setVisibility(View.GONE);
                editText_password.setEnabled(false);
                break;
            case edit:
                button_enter.setVisibility(View.VISIBLE);
                button_enter.setText("修改");
                editText_password.setEnabled(true);
                break;
            case delete:
                button_enter.setVisibility(View.VISIBLE);
                button_enter.setText("删除");
                editText_password.setEnabled(false);
                break;
        }

    }

}
