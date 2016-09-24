
package com.iss.appdemo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.iss.appdemo.bean.User;
import com.iss.appdemo.bean.User.InfoState;
import com.iss.appdemo.view.UserDialog;
import com.iss.appdemo.view.UserDialog.ConfirmAction;
import com.iss.appdemo.view.UserInfoView;

public class UserAdapter extends BaseAdapter {
    private ArrayList<User> beanList;

    private Activity mContext;

    private UserDialog mDialog;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            notifyDataSetChanged();
        }

    };

    public UserAdapter(Activity context) {
        beanList = new ArrayList<User>();
        mContext = context;
        mDialog = new UserDialog(mContext);
    }

    public void addItems(ArrayList<User> list, boolean clear) {
        if (clear) {
            beanList.clear();
        }
        beanList.addAll(list);
        handler.sendEmptyMessage(1);
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public User getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UserInfoView view = null;
        if (convertView != null) {
            view = (UserInfoView) convertView;
        } else {
            view = new UserInfoView(mContext);
        }
        view.setData(beanList.get(position));
        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                mDialog.show(new ConfirmAction() {

                    @Override
                    public void editAction() {
                        beanList.get(position).infoState = InfoState.edit;
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void deleteAction() {
                        beanList.get(position).infoState = InfoState.delete;
                        handler.sendEmptyMessage(1);
                    }
                });
                return false;
            }
        });
        return view;
    }

}
