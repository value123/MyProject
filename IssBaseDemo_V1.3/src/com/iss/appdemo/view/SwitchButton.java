package com.iss.appdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;

public class SwitchButton extends View implements OnTouchListener {

	// 开关开启时的背景，关闭时的背景，滑动按钮
	private Bitmap mSwitchImg, mSwitchSrcImg;

	// 是否正在滑动
	private boolean isSlipping = false;
	// 当前开关状态，true为开启，false为关闭
	private boolean isSwitchOn = false;

	// 手指按下时的水平坐标X，当前的水平坐标X
	private float previousX, currentX;
	
	private boolean isDraw;

	// 开关监听器
	private OnSwitchListener onSwitchListener;
	// 是否设置了开关监听器
	private boolean isSwitchListenerOn = false;

	private int slop;

	private boolean isClickSwitched;

	public SwitchButton(Context context) {
		super(context);
		init();
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setOnTouchListener(this);
		slop = ViewConfiguration.getTouchSlop();
	}

	public void setImageResource(int switchImg) {		
		mSwitchSrcImg = BitmapFactory.decodeResource(getResources(), switchImg);
		isDraw = false;
		ViewTreeObserver vto = getViewTreeObserver();
		vto.addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				if (!isDraw && getWidth() > 0){
					mSwitchImg = Bitmap.createScaledBitmap(mSwitchSrcImg, 
							getWidth() / 2, getHeight(), true);
					if (!mSwitchSrcImg.isRecycled()){
						mSwitchSrcImg.recycle();
						mSwitchSrcImg = null;
					}
					isDraw = true;
					postInvalidate();
				}
				return true;
			}
		});
	}

	public void setSwitchState(boolean switchState) {
		isSwitchOn = switchState;
	}

	public boolean getSwitchState() {
		return isSwitchOn;
	}

	public void updateSwitchState(boolean switchState) {
		isSwitchOn = switchState;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (isDraw){
			Paint paint = new Paint();
			// 滑动按钮的左边坐标
			float left_SlipBtn;

			// 判断当前是否正在滑动
			if (isSlipping) {
				if (currentX > getWidth()) {
					left_SlipBtn = getWidth() / 2;
				} else {
					left_SlipBtn = currentX - getWidth() / 4;
				}
			} else {
				// 根据当前的开关状态设置滑动按钮的位置
				if (isSwitchOn) {
					left_SlipBtn = getWidth() / 2;
				} else {
					left_SlipBtn = 0;
				}
			}

			// 对滑动按钮的位置进行异常判断
			if (left_SlipBtn < 0) {
				left_SlipBtn = 0;
			} else if (left_SlipBtn > getWidth() / 2) {
				left_SlipBtn = getWidth() / 2;
			}

			canvas.drawBitmap(mSwitchImg, left_SlipBtn, 0, paint);
		}		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		// 滑动
		case MotionEvent.ACTION_MOVE:
			if(Math.abs(event.getX()-previousX)>slop) {
				isSlipping = true;
				currentX = event.getX();
			}
			break;

		// 按下
		case MotionEvent.ACTION_DOWN:
			if (event.getX() > getWidth() || event.getY() > getHeight()) {
				return false;
			}
			previousX = event.getX();
			currentX = previousX;
			clickButton(event);
			break;

		// 松开
		case MotionEvent.ACTION_UP:
			if(isSlipping) {
				isSlipping = false;
				slippingUp(event);
			}
			break;

		default:
			break;
		}

		// 重新绘制控件
		invalidate();
		return true;
	}

	/**
	 * 用户滑动开关，up时状态判断处理
	 * @param event
	 */
	private void slippingUp(MotionEvent event) {
		// 松开前开关的状态
		boolean previousSwitchState = isSwitchOn;

		if (event.getX() >= (getWidth() / 2)) {
			isSwitchOn = true;
		} else {
			isSwitchOn = false;
		}

		// 如果设置了监听器，则调用此方法
		if (isSwitchListenerOn && (previousSwitchState != isSwitchOn)) {
			onSwitchListener.onSwitched(isSwitchOn);
		}
	}

	/**
	 * 用户单击控制开关
	 * @param event
	 */
	private void clickButton(MotionEvent event) {
		if (event.getX() >= (getWidth() / 2)) {
			currentX = getWidth()+1;
			if(!isSwitchOn) {
				if(isSwitchListenerOn) {
					isSwitchOn = true;
					onSwitchListener.onSwitched(isSwitchOn);
				}
			}  
		} else {
			currentX = 0;
			if(isSwitchOn) {
				if(isSwitchListenerOn) {
					isSwitchOn = false;
					onSwitchListener.onSwitched(isSwitchOn);
				}
			} 
		}
	}

	public void setOnSwitchListener(OnSwitchListener listener) {
		onSwitchListener = listener;
		isSwitchListenerOn = true;
	}

	public interface OnSwitchListener {
		abstract void onSwitched(boolean isSwitchOn);
	}
}
