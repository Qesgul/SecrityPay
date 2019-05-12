package org.nupter.secritypay;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import org.nupter.secritypay.titlebar.StatusBarUtil;
import org.nupter.secritypay.titlebar.SystemBarTintManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{
    protected Context mContext;
    public static final String url= "http://192.168.31.188:8080/Login/Login";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
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


}
