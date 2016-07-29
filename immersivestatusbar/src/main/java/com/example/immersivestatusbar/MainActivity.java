package com.example.immersivestatusbar;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.immersivestatusbar.adapter.MeRecycleAdapter;
import com.example.immersivestatusbar.adapter.MeRecycleDivider;
import com.example.immersivestatusbar.entity.MeRecycleItem;
import com.lib.utils.DisplayUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private ArrayList<MeRecycleItem> meRecycleItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initDate();
        initRecycleView();
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.arrow_left_circle);
        toolbar.setTitle("压缩布局");

        int statusHeight = DisplayUtil.getStatusHeight(this);
        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.topMargin = statusHeight;
    }

    private void initDate() {

        meRecycleItems = new ArrayList<MeRecycleItem>();
        int[] recycleIcons = {R.mipmap.me_brand, R.mipmap.me_security, R.mipmap.me_service,R.mipmap.me_brand, R.mipmap.me_security, R.mipmap.me_service};
        String[] recycleTitle = getResources().getStringArray(R.array.me_recycle_titles);
        String[] recycleDescription = getResources().getStringArray(R.array.me_recycle_descriptions);
        String[] recycleUrls = getResources().getStringArray(R.array.me_recycle_urls);

        for (int i = 0; i < recycleIcons.length; i++) {
            MeRecycleItem meRecycleItem = new MeRecycleItem(recycleIcons[i], recycleTitle[i], recycleDescription[i], recycleUrls[i]);
            meRecycleItems.add(meRecycleItem);
        }

       /* String[] cs = new String[26];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = String.valueOf((char) ('a' + i));
        }
        ListView listView = (ListView) findViewById(R.id.recycle_view);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, cs));
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }*/
    }

    private void initRecycleView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.addItemDecoration(new MeRecycleDivider(this, LinearLayoutManager.HORIZONTAL));
        recycleView.setHasFixedSize(true);
        MeRecycleAdapter meRecycleAdapter = new MeRecycleAdapter(this, meRecycleItems);
        recycleView.setAdapter(meRecycleAdapter);
    }
}
