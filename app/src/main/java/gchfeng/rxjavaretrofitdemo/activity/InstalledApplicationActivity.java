package gchfeng.rxjavaretrofitdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gchfeng.rxjavaretrofitdemo.R;
import gchfeng.rxjavaretrofitdemo.adapter.BaseListAdapter;

/**
 * Created by gchfeng on 2017/1/10.
 */

public class InstalledApplicationActivity extends Activity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context mContext;
    private BaseListAdapter adapter;

    private List<String> appList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_installed_application);
        ButterKnife.bind(this);
        mContext = InstalledApplicationActivity.this;
        initViews();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseListAdapter(mContext,appList);
        recyclerView.setAdapter(adapter);
    }



}
