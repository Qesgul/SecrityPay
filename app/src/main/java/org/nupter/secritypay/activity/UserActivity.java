package org.nupter.secritypay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.nupter.secritypay.BaseActivity;
import org.nupter.secritypay.R;

import butterknife.BindView;


public class UserActivity extends BaseActivity {
    @BindView(R.id.btn_scan)
    Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView(){

    }

    @Override
    protected void initData(){

    }

    @Override
    protected void initEvent(){
        btn_scan.setOnClickListener(new ChangePicListener());
    }

    class ChangePicListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UserActivity.this, ScanActivity.class);
            startActivity(intent);
        }
    }
}
