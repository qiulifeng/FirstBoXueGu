package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.ExercisesDetailActivity;
import cn.edu.gdmec.android.boxuegu.activity.LoginActivity;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;

/**
 * Created by student on 17/12/25.
 */

public class ExercisesAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExercisesBean> ebl;
    public ExercisesAdapter(Context context){
        this.mContext = context;
    }
    public void setData(List<ExercisesBean> ebl){
        this.ebl = ebl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ebl == null ? 0 :ebl.size();
    }

    @Override
    public ExercisesBean getItem(int position) {
        return ebl ==null? null : ebl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null){
            vh =new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item,null);
            vh.title = (TextView)convertView.findViewById(R.id.tv_title);
            vh.content = (TextView)convertView.findViewById(R.id.tv_content);
            vh.order = (TextView)convertView.findViewById(R.id.tv_order);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        final ExercisesBean bean = getItem(position);
        if (bean!=null){
            vh.order.setText(position+1+"");
            vh.title.setText(bean.title);
            vh.content.setText(bean.content);
            vh.order.setBackgroundResource(bean.background);
        }
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (bean == null)
                    return;
                //课程跳转
                if (readLoginStatus()) {
                    Intent intent = new Intent(mContext, ExercisesDetailActivity.class);
                    intent.putExtra("id", bean.id);
                    mContext.startActivity(intent);
                }else {
                    //Intent intent = new Intent(mContext, LoginActivity.class);
                    //mContext.startActivity(intent);
                    Toast.makeText(mContext, "你还未登录，请先登录", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView title,content;
        public TextView order;
    }
    private boolean readLoginStatus(){
        SharedPreferences sp = mContext.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }
}
