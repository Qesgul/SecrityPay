package org.nupter.secritypay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.nupter.secritypay.BaseActivity;
import org.nupter.secritypay.R;
import org.nupter.secritypay.Utils.NetUtils;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.login)
    Button btn_login;
    @BindView(R.id.register)
    Button btn_register;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login_code;
    }

    @Override
    protected void initView(){

    }

    @Override
    protected void initData(){

    }

    @Override
    protected void initEvent(){
        btn_login.setOnClickListener(new LoginListener());
        btn_register.setOnClickListener(new RegisterListener());
    }

    class LoginListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            NetUtils netUtils = NetUtils.getInstance();
            String url_register = url +"?username="+username.getText().toString()+"&password"+password.getText().toString();
            netUtils.getDataAsynFromNet(url_register, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    Toast.makeText(LoginActivity.this, "登录成功" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, SellerActivity.class);
                    intent.putExtra("sm2Info",result);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }

                @Override
                public void failed(Call call, IOException e) {
                    Toast.makeText(LoginActivity.this, "请求失败，请重新注册" , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class RegisterListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
