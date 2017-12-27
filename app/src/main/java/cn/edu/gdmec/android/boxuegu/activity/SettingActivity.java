package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
    }
    private void init(){
        TextView tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("设置");
        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        RelativeLayout rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#3084FF"));

        RelativeLayout rl_modiy_psw = (RelativeLayout) findViewById(R.id.rl_modiy_psw);
        RelativeLayout rl_security_setting = (RelativeLayout) findViewById(R.id.rl_security_seting);

        RelativeLayout rl_exit_login = (RelativeLayout) findViewById(R.id.rl_exit_login);
        tv_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SettingActivity.this.finish();
            }
        });
        rl_modiy_psw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO：跳转到修改密码的界面
            }
        });
        rl_security_setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO跳转到设置密保的界面
                clearLoginStatus();
            }
        });
        rl_exit_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(SettingActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                clearLoginStatus();
                //把退出登录成功后的状态传递到MainActivity中
                Intent data = new Intent();
                data.putExtra("isLogin",false);
                setResult(RESULT_OK,data);
                SettingActivity.this.finish();
            }
        });

    }
    private void clearLoginStatus(){
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin", false);
        editor.putString("loginUserName","");
        editor.commit();

    }
}
