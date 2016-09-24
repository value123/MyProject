package com.example.immersivestatusbar;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class ToolBarImsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner navigationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar_ims);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        initToolBar();

    }

    private void initToolBar() {
        Resources resources = getResources();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Navigation Icon 要設定在 setSupoortActionBar 才有作用,否則會出現 back button??发现好像不是这样哦
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolBarImsActivity.this, "click navigation", Toast.LENGTH_SHORT).show();
            }
        });
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("ToolBarDemo");
        toolbar.setSubtitle("我是一个子标题");
        toolbar.inflateMenu(R.menu.menu_tool_bar);//添加menu
        toolbar.setOnMenuItemClickListener(menuItemClickListener);
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.category, R.layout.spinner_dropdown_item);
        navigationSpinner = new Spinner(this);
        navigationSpinner.setAdapter(spinnerAdapter);
        toolbar.addView(navigationSpinner, 0);
        final String[] category = getResources().getStringArray(R.array.category);
        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ToolBarImsActivity.this, "you selected: " + category[position], Toast.LENGTH_SHORT).show();

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    //不可使用下面这句话,否则加载的menu布局就是默认的OnCreateOptionMenu()中的
    //setSupportActionBar(toolbar);

    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    Toast.makeText(ToolBarImsActivity.this, "action_edit", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_share:
                    Toast.makeText(ToolBarImsActivity.this, "action_share", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_settings:
                    Toast.makeText(ToolBarImsActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    };

}
