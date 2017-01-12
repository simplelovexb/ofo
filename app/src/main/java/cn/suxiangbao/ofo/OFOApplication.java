package cn.suxiangbao.ofo;

import android.app.Application;

/**
 * 整个程序定制的Application对象。
 * Created by JianHuang
 * Date: 2015/1/13
 * Time: 11:16
 */
public class OFOApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        if (!OFOContext.isInitialized()) {
            OFOContext.init(getApplicationContext());
        }
    }
}
