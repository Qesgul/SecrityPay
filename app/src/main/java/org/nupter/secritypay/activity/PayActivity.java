package org.nupter.secritypay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.nupter.secritypay.BaseActivity;
import org.nupter.secritypay.R;

import butterknife.BindView;


public class PayActivity extends BaseActivity {
    @BindView(R.id.order_name_tv)
    TextView tv_name;
    @BindView(R.id.order_amount_tv)
    TextView tv_order_price;
    @BindView(R.id.pay_amount_tv)
    TextView tv_pay_price;
    @BindView(R.id.pay_tv)
    TextView btn_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void initView(){

    }

    @Override
    protected void initData(){
		tv_name.setText(goodsInfo.getName());
		tv_order_price.setText(goodsInfo.getPrice());
		tv_pay_price.setText(goodsInfo.getPrice());
    }

    @Override
    protected void initEvent(){
        btn_pay.setOnClickListener(new ChangePicListener());
    }

    class ChangePicListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplication(),"支付成功",Toast.LENGTH_SHORT).show();
        }
    }
}
