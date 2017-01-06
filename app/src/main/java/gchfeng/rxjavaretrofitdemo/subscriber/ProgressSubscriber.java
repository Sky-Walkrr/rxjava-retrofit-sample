package gchfeng.rxjavaretrofitdemo.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by walker on 2017/1/6.
 */

public class ProgressSubscriber<T> extends Subscriber<T>{

    private SubscribeOnNextListener subscribeOnNextListener;
    private Context context;
    private ProgressDialog dialog;

    public ProgressSubscriber(SubscribeOnNextListener subscribeOnNextListener, Context context) {
        this.subscribeOnNextListener = subscribeOnNextListener;
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = new ProgressDialog(context);
        dialog.setMessage("please wait...");
        dialog.show();
    }

    @Override
    public void onCompleted() {
        Toast.makeText(context, "onComplete", Toast.LENGTH_SHORT).show();
        if (dialog != null) {
            dialog.dismiss();
        }

    }

    @Override
    public void onError(Throwable e) {
        Log.e("","requestOnFailure", e);
        if (dialog != null) {
            dialog.dismiss();
        }

    }

    @Override
    public void onNext(T t) {
        subscribeOnNextListener.onNext(t);
    }
}
