package org.nupter.secritypay;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import org.bouncycastle.math.ec.ECPoint;
import org.nupter.secritypay.bean.Message;
import org.nupter.secritypay.bean.SM2KeyPair;
import org.nupter.secritypay.crypto.SM2;
import org.nupter.secritypay.titlebar.StatusBarUtil;
import org.nupter.secritypay.titlebar.SystemBarTintManager;

import java.io.File;
import java.math.BigInteger;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{
    protected Context mContext;
    public SM2 sm2;
    public SM2KeyPair sm2KeyPair;
    protected Message goodsInfo;
    protected SharedPreferences preference;
    protected SharedPreferences.Editor editor;
    protected Boolean flag;
    protected String savePath = getSDCardPath()+"/size/";
    private Handler handler=new Handler(); //在主线程中创建handler
    private ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getKey();
        setPermission();
        setNotificationBar();
        setViewLayout();
        StatusBarUtil.StatusBarDarkMode(this);
        initView();
        initData();
        initEvent();
    }

    protected void initView(){}

    protected void initData(){
    }

    protected void initEvent(){}

    protected void setPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请权限  第二个参数是一个 数组 说明可以同时申请多个权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.CAMERA}, 1);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.CAMERA}, 1);
        }
    }

    protected void setViewLayout() {
        setContentView(getContentViewResId());
		ButterKnife.bind(this);
    }

    protected abstract int getContentViewResId();

    protected void setNotificationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(getNotificationBg());// 通知栏所需颜色
        }
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected int getNotificationBg() {
        return R.color.transparent;
    }

    protected void getKey() {
        preference = getSharedPreferences("User", Context.MODE_PRIVATE);
        flag = preference.getBoolean("firststart", false);
        if (flag) {
            new Thread(){//创建一个新的线程
                public void run(){
                    try {
                        handler.post(new Runnable() {//此处用一个匿名内部类，runnable自动把消息发送给主线程创建处理的handler，主线程会自动更新。
                            public void run() {
                                dialog=new ProgressDialog(mContext);
                                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置进度条样式
                                dialog.setMessage("请稍候...");
                                dialog.setCancelable(false);//判断是否取消进度条
                                dialog.show();
                            }
                        });
                        ECPoint publicKey = sm2.importPublicKey(savePath+"publickey.pem");
                        BigInteger privateKey = sm2.importPrivateKey(savePath+"privatekey.pem");
                        sm2KeyPair = new SM2KeyPair(publicKey,privateKey);
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    /**
     * 获取SDCard的目录路径功能
     */
    private String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }
}
