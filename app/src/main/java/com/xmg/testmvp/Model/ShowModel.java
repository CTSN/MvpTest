package com.xmg.testmvp.Model;

/**
 * Created by xmg on 2016/9/22.
 */

public class ShowModel implements ShowModelIpl{

    //模拟耗时操作
    @Override
    public void showText(final ShowListerner listerner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listerner.showText("显示成功");

            }
        }).start();
    }
}
