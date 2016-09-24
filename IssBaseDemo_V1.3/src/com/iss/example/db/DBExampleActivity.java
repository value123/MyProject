
package com.iss.example.db;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.iss.app.IssActivity;
import com.iss.appdemo.R;
import com.iss.appdemo.adapter.UserAdapter;
import com.iss.appdemo.bean.User;
import com.iss.appdemo.db.DBUtil;
import com.iss.utils.PhoneInfo;
import com.iss.view.common.ToastAlone;

public class DBExampleActivity extends IssActivity {
    private EditText editText_name;

    private EditText editText_password;

    private Button button_enter;

    private ListView mListView;

    private UserAdapter mAdapter;
    private LinearLayout layout_root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
    }

    @Override
    protected void setListener() {
        ContentResolver mResolver = getContentResolver();
        mResolver.registerContentObserver(DBUtil.URI_USER, false, new ContentObserver(null) {

            @Override
            public void onChange(boolean selfChange) {
                refreshData();
            }

        });
        
        button_enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PhoneInfo.hideSoftInputMode(getApplicationContext(),layout_root.findFocus());
                String name = editText_name.getText().toString();
                String password = editText_password.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    ToastAlone.showToast(getApplicationContext(), "请填写完整", Toast.LENGTH_SHORT);
                    return;
                }
                User user = new User(name, password);
                try {
                    boolean result = DBUtil.addUser(getApplicationContext(), user);
                    if (result) {
                        ToastAlone.showToast(getApplicationContext(), "写入数据成功", Toast.LENGTH_LONG);
                    } else {
                        ToastAlone.showToast(getApplicationContext(), "更新数据成功", Toast.LENGTH_LONG);
                    }
                } catch (Exception e) {
                    ToastAlone.showToast(getApplicationContext(), "写入失败", Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new UserAdapter(this);
        mListView.setAdapter(mAdapter);
        refreshData();
    }

    @Override
    protected void initView() {
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_password = (EditText) findViewById(R.id.editText_password);
        button_enter = (Button) findViewById(R.id.button_enter);
        mListView = (ListView) findViewById(R.id.listView_users);
        
        layout_root = (LinearLayout)findViewById(R.id.layout_root);

    }

    private void refreshData() {
        ArrayList<User> userList = DBUtil.getUsers(this);
        mAdapter.addItems(userList, true);
    }

}
