package org.nupter.secritypay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.nupter.secritypay.BaseActivity;
import org.nupter.secritypay.R;
import org.nupter.secritypay.Utils.Base64Utils;
import org.nupter.secritypay.Utils.NetUtils;
import org.nupter.secritypay.bean.SM2KeyPair;
import org.nupter.secritypay.bean.SM2User;
import org.nupter.secritypay.crypto.SM2;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;


public class RegisterActivity extends BaseActivity {
    @BindView(R.id.register)
    Button btn_register;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.seller)
    EditText seller;
    SM2User sm2User = new SM2User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(){

    }

    @Override
    protected void initData(){

    }

    @Override
    protected void initEvent(){
        btn_register.setOnClickListener(new RegisterListener());
    }

    class RegisterListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            createUser();
            NetUtils netUtils = NetUtils.getInstance();
            String url_register = url +"?flag=0"+ "&username="+username.getText().toString()+"&password"+password.getText().toString()+"&M="+seller.getText().toString();
            Map<String, String> reqBody = new ConcurrentSkipListMap<>();
            reqBody.put("username", username.getText().toString());
            reqBody.put("password",password.getText().toString());
            reqBody.put("M", sm2User.getM());
            reqBody.put("Signature",sm2User.getSignature());
            reqBody.put("PublicKey",sm2User.getPublicKeyStr());
            reqBody.put("PrivateKey",sm2User.getPrivateKeyStr());
            netUtils.postDataAsynToNet(url_register,reqBody, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    if(result!=null&&result.equals("success")){
                        Toast.makeText(RegisterActivity.this, "注册成功" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        RegisterActivity.this.finish();
                    }else Toast.makeText(RegisterActivity.this, "请求失败，请重新注册" , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failed(Call call, IOException e) {
                    Toast.makeText(RegisterActivity.this, "请求失败，请重新注册" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected void createUser(){
        SM2 x = new SM2();
        SM2KeyPair keys = x.generateKeyPair();
        String IDA = "Size";
        String M = seller.getText().toString();
        SM2.Signature signature = x.sign(M, IDA, keys);
        sm2User.setIDA(IDA);
        sm2User.setM(M);
        sm2User.setSignature(signature.toString());
        sm2User.setPublicKeyStr(Base64Utils.encode(keys.getPublicKey().getEncoded(false)));
        sm2User.setPrivateKeyStr(String.valueOf(keys.getPrivateKey()));
    }
}
