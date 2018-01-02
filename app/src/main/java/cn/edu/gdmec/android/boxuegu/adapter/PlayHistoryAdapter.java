package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.VideoPlayActivity;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;

/**
 * Created by student on 18/1/2.
 */

public class PlayHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<VideoBean> vbl;
    public PlayHistoryAdapter(Context context){
        this.mContext =context;
    }

    public void setData(List<VideoBean> vbl){
        this.vbl = vbl;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return vbl == null ? 0 : vbl.size();
    }

    @Override
    public VideoBean getItem(int position) {

        return vbl == null ? null : vbl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View converView, ViewGroup parent) {
        final ViewHolder vh;
        if (converView == null){
            vh = new ViewHolder();
            converView = LayoutInflater.from(mContext).inflate(R.layout.play_history_list_item,null);
            vh.tv_title = (TextView) converView.findViewById(R.id.tv_adapter_title);
            vh.tv_video_title = (TextView) converView.findViewById(R.id.tv_video_title);
            vh.iv_icon = (ImageView) converView.findViewById(R.id.iv_video_icon);
            converView.setTag(vh);
        }else {
            vh = (ViewHolder) converView.getTag();
        }
        final VideoBean bean = getItem(position);
        if (bean!=null){
            vh.tv_title.setText(bean.title);
            vh.tv_video_title.setText(bean.secondTitle);
            switch (bean.chapterId){
                case 1:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon1);
                    break;
                case 2:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon2);
                    break;
                case 3:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon3);
                    break;
                case 4:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon4);
                    break;
                case 5:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon5);
                    break;
                case 6:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon6);
                    break;
                case 7:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon7);
                    break;
                case 8:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon8);
                    break;
                case 9:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon9);
                    break;
                case 10:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon10);
                    break;
                default:
                    vh.iv_icon.setImageResource(R.drawable.video_play_icon1);
                    break;
            }
        }
        converView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean == null)
                    return;
                Intent intent = new Intent(mContext, VideoPlayActivity.class);
                intent.putExtra("videoPath",bean.videoPath);
                mContext.startActivity(intent);
            }
        });
        return converView;
    }
    class ViewHolder{
        public TextView tv_title,tv_video_title;
        public ImageView iv_icon;
    }
}

