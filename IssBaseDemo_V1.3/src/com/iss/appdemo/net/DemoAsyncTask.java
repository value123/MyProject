
package com.iss.appdemo.net;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

public abstract class DemoAsyncTask<Params, Progress, Result> extends
        AsyncTask<Params, Progress, Result> {
    private LoadingDialog ld;
    protected String exception;

    public DemoAsyncTask(Activity activity) {
        this(activity, true);
    }
    
    
    public DemoAsyncTask(Activity activity, final DialogInterface.OnCancelListener l) {
        this(activity, l, true);
    }

    public DemoAsyncTask(Activity activity, final boolean cancelable) {
        this(activity, null, cancelable);
    }

    public DemoAsyncTask(Activity activity, final DialogInterface.OnCancelListener l,
            final boolean cancelable) {
        super();
        ld = new LoadingDialog(activity);
        ld.setCancelable(cancelable);
        ld.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (cancelable) {
                    DemoAsyncTask.this.cancel(true);
                }
                if (l != null) {
                    l.onCancel(dialog);
                }
            }
        });
    }

    @Override
    protected Result doInBackground(Params... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        ld.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ld.show();
    }

}
