package com.common.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import butterknife.Unbinder;

/**
 * Created by jiayiyang on 17/5/8.
 */

public class ActivityBean implements Parcelable {

    private Unbinder unbinder;

    public void setUnbinder(Unbinder unbinder){
        this.unbinder = unbinder;
    }

    public Unbinder getUnbinder(){
        return unbinder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable((Serializable) unbinder);
    }

    public static final Creator<ActivityBean> CREATOR = new Creator<ActivityBean>() {
        @Override
        public ActivityBean createFromParcel(Parcel source) {
            ActivityBean activityBean = new ActivityBean();
            activityBean.setUnbinder((Unbinder)source.readSerializable());
            return activityBean;
        }

        @Override
        public ActivityBean[] newArray(int size) {
            return new ActivityBean[size];
        }
    };
}
