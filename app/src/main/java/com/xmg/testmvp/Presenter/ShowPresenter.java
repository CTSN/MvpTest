package com.xmg.testmvp.Presenter;

import com.xmg.testmvp.Model.ShowListerner;
import com.xmg.testmvp.Model.ShowModel;
import com.xmg.testmvp.View.MainView;

import java.util.logging.Handler;

/**
 * Created by xmg on 2016/9/22.
 */

public class ShowPresenter {
    private ShowModel model;
    private MainView mainView;
    private android.os.Handler handler;
    public ShowPresenter(MainView mainView){
        this.mainView = mainView;
        model = new ShowModel();
        handler = new android.os.Handler();
    }

    public void Show(){
        model.showText(new ShowListerner() {
            @Override
            public void showText(final String text) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mainView.Onshow(text);
                    }
                });
            }
        });
    }

}
