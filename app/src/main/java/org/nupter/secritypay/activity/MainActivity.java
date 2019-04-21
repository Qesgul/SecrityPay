package org.nupter.secritypay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.bouncycastle.math.ec.ECCurve;
import org.nupter.secritypay.BaseActivity;
import org.nupter.secritypay.R;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.seller)
    Button btn_seller;
    @BindView(R.id.user)
    Button btn_user;
    private static ECCurve.Fp curve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(){

    }

    @Override
    protected void initData(){
    }

    @Override
    protected void initEvent(){
        btn_seller.setOnClickListener(new SellerListener());
        btn_user.setOnClickListener(new UserPicListener());
    }

    class SellerListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SellerActivity.class);
            startActivity(intent);
        }
    }

    class UserPicListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }
    }

}
