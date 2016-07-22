package com.example.immersivestatusbar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.immersivestatusbar.R;
import com.example.immersivestatusbar.entity.MeRecycleItem;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shenwenjie on 6/7/2016.
 */
public class MeRecycleAdapter extends RecyclerView.Adapter {


    private ArrayList<MeRecycleItem> meRecycleItems;
    private Context mContext;

    public MeRecycleAdapter(Context context, ArrayList<MeRecycleItem> meRecycleItems) {
        this.mContext = context;
        this.meRecycleItems = meRecycleItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MeRecycleHolder meRecycleHolder = new MeRecycleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.me_recycle_item, parent, false));
        return meRecycleHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MeRecycleItem meRecycleItem = meRecycleItems.get(position);
        MeRecycleHolder viewHolder = (MeRecycleHolder) holder;
        viewHolder.ivIcon.setBackgroundResource(meRecycleItem.getIconId());
        viewHolder.tvTitle.setText(meRecycleItem.getTitle());
        viewHolder.tvDescription.setText(meRecycleItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return isEmpty() ? 0 : meRecycleItems.size();
    }

    public boolean isEmpty() {
        return meRecycleItems.isEmpty() || meRecycleItems.size() == 0;

    }

    class MeRecycleHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_container)
        LinearLayout llContainer;
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.rl_more)
        RelativeLayout rlMore;
        @InjectView(R.id.tv_description)
        TextView tvDescription;

        public MeRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MeRecycleItem item = meRecycleItems.get(getAdapterPosition());
//                    mContext.startActivity(WebViewActivity.buildIntent(mContext, item.url, "", false));
                }
            });
        }
    }
}
