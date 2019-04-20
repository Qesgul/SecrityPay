package org.nupter.secritypay.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import org.nupter.secritypay.BaseActivity;
import org.nupter.secritypay.R;
import org.nupter.secritypay.Utils.Base64Utils;
import org.nupter.secritypay.Utils.EncodingHandler;
import org.nupter.secritypay.bean.Message;
import org.nupter.secritypay.bean.SM2KeyPair;
import org.nupter.secritypay.crypto.SM2;

import butterknife.BindView;


public class SellerActivity extends BaseActivity {
    @BindView(R.id.msg_goods)
    EditText msg_goods;
    @BindView(R.id.msg_price)
    EditText msg_price;
    @BindView(R.id.btn_QRcore)
    Button btn_QRcore;
    @BindView(R.id.img_QRcore)
    ImageView img_QRcore;


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
			editor = preference.edit();
			editor.putBoolean("firststart", true);
			editor.commit();
			if(!flag){
				SM2 x = new SM2();
				SM2KeyPair keys = x.generateKeyPair();
				x.exportPrivateKey(keys.getPrivateKey(),savePath);
				x.exportPublicKey(keys.getPublicKey(),savePath);
			}
			try {
				byte[] nameText = sm2.encrypt(new Gson().toJson(msg),sm2KeyPair.getPublicKey());
				Bitmap QRcore = EncodingHandler.createQRCode("size"+"#@%"+ Base64Utils.encode(nameText), 500);
				img_QRcore.setImageBitmap(QRcore);
			} catch (WriterException e) {
				e.printStackTrace();
			}
		}
    }
}
