package gchfeng.rxjavaretrofitdemo.entity;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gchfeng on 2017/1/10.
 */

public class AppInfo implements Parcelable {

    private String appName;
    private Drawable appIcon;

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", appIcon=" + appIcon +
                '}';
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appName);
        dest.writeParcelable((Parcelable) this.appIcon, flags);
    }

    public AppInfo() {
    }

    protected AppInfo(Parcel in) {
        this.appName = in.readString();
        this.appIcon = in.readParcelable(Drawable.class.getClassLoader());
    }

    public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
}
