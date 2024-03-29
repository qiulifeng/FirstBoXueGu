package cn.edu.gdmec.android.boxuegu.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


import cn.edu.gdmec.android.boxuegu.bean.CourseBean;
import cn.edu.gdmec.android.boxuegu.fragment.AdBannerFragment;
import cn.edu.gdmec.android.boxuegu.view.CourseView;

/**
 * Created by student on 17/12/28.
 */

public class AdBannerAdapter extends FragmentStatePagerAdapter implements View.OnTouchListener{

private List<CourseBean> cadl;
    private Handler mHanlder;

    public AdBannerAdapter(FragmentManager fm) {
        super(fm);
        cadl = new ArrayList<CourseBean>();
    }
    public AdBannerAdapter(FragmentManager fm,Handler handler) {
        super(fm);
        mHanlder = handler;
        cadl = new ArrayList<CourseBean>();

    }
    /**
     *
     * @param position
     * @return
     */

    public void setDatas(List<CourseBean> cad1){
        this.cadl = cad1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        //防止刷新结果显示列表时出现缓存数据，重载此方法，使之默认返回POSITION_NONE

        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        if (cadl.size()>0){
            args.putString("ad", cadl.get(position % cadl.size()).icon);
        }
        return AdBannerFragment.newInstance(args);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    public int getSize(){
        return cadl == null ? 0 : cadl.size();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mHanlder.removeMessages(CourseView.MSG_AD_SLID);
        return false;
    }
}
