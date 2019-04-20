package org.nupter.secritypay.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class BasicPresenter<V> {

    protected Reference<V> mViewRef;//View 接口类型的弱引用

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
