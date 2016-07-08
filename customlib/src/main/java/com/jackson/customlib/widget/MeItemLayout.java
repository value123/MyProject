package com.jackson.customlib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jackson.customlib.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shenwenjie on 7/7/2016.
 */
public class MeItemLayout extends LinearLayout {
    @InjectView(R.id.v_header_divider)
    View vHeaderDivider;
    @InjectView(R.id.iv_icon)
    ImageView ivIcon;
    @InjectView(R.id.iv_go)
    ImageView ivGo;
    @InjectView(R.id.tv_text)
    TextView tvText;
    @InjectView(R.id.rl_content)
    RelativeLayout rlContent;
    @InjectView(R.id.v_footer_divider)
    View vFooterDivider;

    public MeItemLayout(Context context) {
        this(context, null);
    }

    public MeItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.me_item_layout, this, true);
        ButterKnife.inject(this);
        initAttrs(context, attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View view = LayoutInflater.from(context).inflate(R.layout.me_item_layout, this, true);
        ButterKnife.inject(this);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        boolean showTopDivider = true;
        boolean showBottomDivider = false;
        Drawable leftIconDrawable =null;
        Drawable rightIconDrawable = null;
        CharSequence contentText = "";
        ColorStateList textColor = null;
        int textSize = 16;
        CharSequence text = "";
        if (null != attrs) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MeItemLayout);
            int count = attributes.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = attributes.getIndex(i);
                switch (attr) {
                    case R.styleable.MeItemLayout_showTopDivider:
                        showTopDivider = attributes.getBoolean(attr, true);
                        break;
                    case R.styleable.MeItemLayout_showBottomDivider:
                        showBottomDivider = attributes.getBoolean(attr, false);
                        break;
                    case R.styleable.MeItemLayout_leftIcon:
                        leftIconDrawable = attributes.getDrawable(attr);
                        break;
                    case R.styleable.MeItemLayout_rightIcon:
                        rightIconDrawable = attributes.getDrawable(attr);
                        break;
                    case R.styleable.MeItemLayout_contentText:
                        contentText = attributes.getText(attr);
                        break;
                    case R.styleable.MeItemLayout_textColor:
                        textColor = attributes.getColorStateList(attr);
                        break;
                    case R.styleable.MeItemLayout_textSize:
                        textSize = attributes.getDimensionPixelSize(attr, textSize);

                        break;
                }
            }
        }
        vHeaderDivider.setVisibility(showTopDivider?View.VISIBLE:View.GONE);
        vFooterDivider.setVisibility(showBottomDivider?View.VISIBLE:View.GONE);
        ivIcon.setImageDrawable(leftIconDrawable);
        ivGo.setImageDrawable(rightIconDrawable);
        tvText.setText(contentText.toString());
        tvText.setTextSize(textSize);
        tvText.setTextColor(textColor != null ? textColor : ColorStateList.valueOf(0xFF000000));
    }

}
