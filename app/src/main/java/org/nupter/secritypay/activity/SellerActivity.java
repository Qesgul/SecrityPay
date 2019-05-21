package org.nupter.secritypay.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import org.nupter.secritypay.BaseActivity;
import org.nupter.secritypay.R;
import org.nupter.secritypay.Utils.EncodingHandler;
import org.nupter.secritypay.bean.Message;

import butterknife.BindView;


public class SellerActivity extends BaseActivity {
    @BindView(R.id.msg_goods)
    EditText msg_goods;
    @BindView(R.id.msg_price)
    EditText msg_price;
	@BindView(R.id.tv_seller)
	TextView msg_seller;
    @BindView(R.id.btn_QRcore)
    Button btn_QRcore;
    @BindView(R.id.img_QRcore)
    ImageView img_QRcore;
	String sm2User;
    String tv_seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_seller;
    }

    @Override
    protected void initView(){

    }

    @Override
    protected void initData(){
		Intent intent = getIntent();
		sm2User = intent.getStringExtra("sm2Info");
		String []msg=sm2User.split("#");
		tv_seller = msg[1];
		msg_seller.setText(tv_seller);
    }

    @Override
    protected void initEvent(){
        btn_QRcore.setOnClickListener(new ChangePicListener());
        
    }

    class ChangePicListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
			Message msg = new Message();
			msg.setName(msg_goods.getText().toString());
            msg.setPrice(msg_price.getText().toString());
			try {
				Bitmap QRcore = EncodingHandler.createQRCode(sm2User+"#"+ new Gson().toJson(msg), 500);
				img_QRcore.setImageBitmap(QRcore);
			} catch (WriterException e) {
				e.printStackTrace();
			}
		}
    }
}
