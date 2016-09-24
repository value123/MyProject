package com.iss.appdemo.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.iss.view.waterfall.NetImageView;

public class WaterImageView extends NetImageView {

	public WaterImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public WaterImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WaterImageView(Context context) {
		super(context);
		init();
	}

	private Rect rec;
	private Paint paint;

	public void init() {
		rec = new Rect();
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(10);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		rec.top = 0;
		rec.bottom = getMeasuredHeight();
		rec.right = getMeasuredWidth();
		rec.left = 0;
		canvas.drawRect(rec, paint);
	}

}
