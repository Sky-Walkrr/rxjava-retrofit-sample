package gchfeng.rxjavaretrofitdemo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gchfeng.rxjavaretrofitdemo.R;
import gchfeng.rxjavaretrofitdemo.databinding.BaseListAdapterBinding;
import gchfeng.rxjavaretrofitdemo.entity.AppInfo;

/**
 * Created by gchfeng on 2017/1/10.
 */

public class BaseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AppInfo> dataList = new ArrayList<>();
    private BaseListAdapterBinding baseListAdapterBinding;

    public BaseListAdapter(Context mContext, List<AppInfo> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    public void setDataList(List<AppInfo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_base_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AppInfo info = dataList.get(position);
        if (holder instanceof ViewHolder) {
            baseListAdapterBinding.tvText.setText(TextUtils.isEmpty(info.getAppName()) ? "" : info.getAppName());
            Drawable icon = info.getAppIcon();
            icon.setBounds(0,0,50,50);
            baseListAdapterBinding.tvText.setCompoundDrawables(icon,null,null,null);
            baseListAdapterBinding.tvText.setCompoundDrawablePadding(20);
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            baseListAdapterBinding = BaseListAdapterBinding.bind(itemView);

        }
    }
}
