package com.example.immersivestatusbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.immersivestatusbar.fragment.BlankFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    @InjectView(R.id.btn1)
    Button btn1;
    @InjectView(R.id.btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.inject(this);



//        Window window = getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            //将状态栏设置成全透明
//            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            Log.d(TAG,"bits="+Integer.toHexString(bits)+">>params.flags="+Integer.toHexString(params.flags));
//            Log.d(TAG,"params.flags & bits"+Integer.toHexString((params.flags & bits)));
////            if ((params.flags & bits) == 0) {
////
//////                params.flags |= bits;
////                //如果是取消全透明，
////                 params.flags &= ~bits;
////                window.setAttributes(params);
////            }
////            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
////            params.flags |= bits;
////            //如果是取消全透明，params.flags &= ~bits;
////            window.setAttributes(params);
//        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                getSupportFragmentManager().beginTransaction().add(R.id.ll_container,BlankFragment.newInstance("1",""),"1").commitAllowingStateLoss();

                break;
            case R.id.btn2:
                getSupportFragmentManager().beginTransaction().add(R.id.ll_container,BlankFragment.newInstance("2",""),"2").commitAllowingStateLoss();
                break;
        }
    }
}
