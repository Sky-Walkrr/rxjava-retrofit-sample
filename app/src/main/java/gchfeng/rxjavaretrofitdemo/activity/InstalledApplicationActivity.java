package gchfeng.rxjavaretrofitdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gchfeng.rxjavaretrofitdemo.R;
import gchfeng.rxjavaretrofitdemo.adapter.BaseListAdapter;
import gchfeng.rxjavaretrofitdemo.entity.AppInfo;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by gchfeng on 2017/1/10.
 */

public class InstalledApplicationActivity extends Activity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context mContext;
    private BaseListAdapter adapter;

    private List<String> appNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_installed_application);
        ButterKnife.bind(this);
        mContext = InstalledApplicationActivity.this;
        initViews();
        getInstalledApps();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseListAdapter(mContext,appNameList);
        recyclerView.setAdapter(adapter);
    }

    private void getInstalledApps() {
        final PackageManager pm = getPackageManager();
        final List<ApplicationInfo> appList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Observable.from(appList)
                .filter(new Func1<ApplicationInfo, Boolean>() {
                    @Override
                    public Boolean call(ApplicationInfo applicationInfo) {
                        return (applicationInfo.flags & applicationInfo.FLAG_SYSTEM) <= 0;
                    }
                })
                .map(new Func1<ApplicationInfo, AppInfo>() {
                    @Override
                    public AppInfo call(ApplicationInfo applicationInfo) {
                        AppInfo appInfo = new AppInfo();
                        appInfo.setAppName(applicationInfo.loadLabel(pm).toString());
                        appInfo.setAppIcon(applicationInfo.loadIcon(pm));
                        return appInfo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        adapter.setDataList(appNameList);
                        Toast.makeText(mContext, "load info complete~", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("","onError",e);
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        appNameList.add(appInfo.getAppName());
                    }
                });
    }

}
