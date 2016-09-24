package com.iss.appdemo.activity;

import android.content.Intent;

import com.iss.app.IssActivity;
import com.iss.appdemo.AppContext;
import com.iss.appdemo.bean.Demo;

public abstract class EgActivity extends IssActivity {

    public void startActivity(Demo demo) {
        if (demo != null && demo.s != null) {
            Intent intent = new Intent(this, demo.s);
            if (demo.pics != null) {
                intent.putExtra(AppContext.INTENT_DESC_IMAGES, demo.pics);
            }
            startActivity(intent);
        }
    }

}
