package cn.edu.gdmec.android.boxuegu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.adapter.VideoListAdapter;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;

public class VideoListActivity extends AppCompatActivity {

    private int chapterId;
    private String intro;
    private ArrayList<VideoBean> videoList;
    private TextView tv_inttro;
    private TextView tv_video;
    private ListView lv_video_list;
    private TextView tv_chapter_intro;
    ScrollView sv_chapter_intro;
    private VideoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        //界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //从课程界面传递过来的章节id

        chapterId = getIntent().getIntExtra("id",0);
        //从课程界面传递过来的章节简介
        intro = getIntent().getStringExtra("intro");
        db = DBUtils.getInstance(VideoListActivity.this);
        initData();
        initView();
    }

    private void initView() {
        tv_inttro = (TextView) findViewById(R.id.tv_intro);
        tv_video = (TextView) findViewById(R.id.tv_video);
        lv_video_list = (ListView) findViewById(R.id.lv_video_list);

        tv_chapter_intro = (TextView) findViewById(R.id.tv_chapter_intro);
        sv_chapter_intro = (ScrollView) findViewById(R.id.sv_chapter_intro);
        adapter = new VideoListAdapter(this, new VideoListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, ImageView iv) {
                adapter.setSelectedPosition(position);
                VideoBean bean = videoList.get(position);
               String videoPath =  bean.videoPath;
                adapter.notifyDataSetChanged();
                if (TextUtils.isEmpty(videoPath)){
                    Toast.makeText(VideoListActivity.this,"本地没有此视频，暂时无法播放",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    //判断用户是否登录，若登录则把此视频添加到数据库
                    if (readLoginStatus()){
                        String userName = AnalysisUtils.readLoginUserName(VideoListActivity.this);
                        db.saveVideoPlayList(videoList.get(position),userName);
                        
                    }
                    //跳转到视频播放界面
                }
            }
        });



    }

    private boolean readLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }

    private void initData() {
        JSONArray jsonArray;
        InputStream is;
        try {
            is = getResources().getAssets().open("data.json");
            jsonArray = new JSONArray(read(is));
            videoList = new ArrayList<VideoBean>();
            for (int i=0;i<jsonArray.length();i++){
                VideoBean bean = new VideoBean();
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                if (jsonObj.getInt("chapterId")==chapterId){
                    bean.chapterId = jsonObj.getInt("chapterId");
                    bean.videoId = Integer.parseInt(jsonObj.getString("videoId"));
                    bean.title = jsonObj.getString("title");
                    bean.secondTitle = jsonObj.getString("secondTitle");
                    bean.videoPath = jsonObj.getString("videoPath");
                    videoList.add(bean);
                }
                bean = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取数据流，参数in是数据流
     * @param is
     * @return
     */

    private String read(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = null;
        String line = null;

        try {
            sb = new StringBuilder(); // 实例化一个stringbuilder对象
            //用InputStreamReader把in这个字节流转换成字符流BufferREADER
            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
            return " ";
        } finally {
            if (in!= null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();

            }
        }
if (reader!=null){
    try {
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
return sb.toString();
    }
}
