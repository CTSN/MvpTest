package com.xmg.testmvp.Presenter;

import com.xmg.testmvp.Model.ShowListerner;
import com.xmg.testmvp.Model.ShowModel;
import com.xmg.testmvp.View.ShowView;

/**
 * Created by xmg on 2016/9/22.
 */

public class ShowPresenter {
    private ShowModel model;
    private ShowView mainView;
    private android.os.Handler handler;
    public ShowPresenter(ShowView mainView){
        this.mainView = mainView;
        model = new ShowModel();
        handler = new android.os.Handler();
    }

    public void Show(){
        model.showText(new ShowListerner() {
            @Override
            public void success(final String text) {

                //更新UI在主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //返回给MainActivity
                        mainView.Onshow(text);
                    }
                });
            }
        });
    }

}
