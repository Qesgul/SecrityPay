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
import org.nupter.secritypay.bean.SM2KeyString;
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
			SM2 x = new SM2();
			SM2KeyPair keys = x.generateKeyPair();
			if (flag) {
				saveKey(keys);
			}
			try {
				Bitmap QRcore = EncodingHandler.createQRCode("size"+"#@%"+ Base64Utils.encode(x.encrypt(new Gson().toJson(msg),keys.getPublicKey())), 500);
//				Bitmap QRcore = EncodingHandler.createQRCode("size"+"#@%"+ new Gson().toJson(msg), 500);
				img_QRcore.setImageBitmap(QRcore);
			} catch (WriterException e) {
				e.printStackTrace();
			}
		}
    }
    protected void saveKey (SM2KeyPair keys){
		SM2KeyString sm2_str = new SM2KeyString();
		sm2_str.setPublicKeyStr(Base64Utils.encode(keys.getPublicKey().getEncoded(false)));
		sm2_str.setPrivateKeyStr(String.valueOf(keys.getPrivateKey()));
		String keyString = new Gson().toJson(sm2_str);
		editor = preference.edit();
		//将登录标志位设置为false，下次登录时不在显示首次登录界面
		editor.putBoolean("firststart", false);
		editor.putString("Key", keyString);
		editor.commit();
	}
}
