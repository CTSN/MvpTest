# MVP的简单理解#

###概述###

####为什么使用MVP？####

使代码结构逻辑更加清晰，业务逻辑与界面的剥离，解耦。

####MVP理解####
 
- View **对应于Activity、Fragment**，负责View的绘制以及与用户交互
- Model 业务逻辑和实体模型
- Presenter 负责完成View于Model间的交互

让View与Model之间完全剥离，通过Presenter作为媒介，使两者起交互作用。

举个例子：

加载一串数据显示在TextView中，Model就是负责去加载这个数据，View就是显示TextView,如何把数据显示到TextView中呢？ 就是通过Presenter去调用Model加载数据，然后通过接口返回给View。


![](http://i.imgur.com/1uagJPk.png)


根据这上面这个例子，按照流程编写成一个简单demo

整体目录结构

![](http://i.imgur.com/Z31BsUH.png)

（1）首先要去加载数据,编写Model层

	package com.xmg.testmvp.Model;
	
	/**
	 * Created by xmg on 2016/9/22.
	 */
	
	public interface ShowListerner {

    	void success(String text);
	}

-------------------------------


	package com.xmg.testmvp.Model;

	/**
	 * Created by xmg on 2016/9/22.
	 */
	
	public interface ShowModelIpl {
	
	    void showText(ShowListerner listerner);
	}

-----------------------------

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
	                listerner.success("显示成功");
	
	            }
	        }).start();
    	}
	}

上述代码中模拟网络请求耗时的操作，通过ShowListerner的回调返回一个显示的数据结果。而ShowModelIpl是一个业务接口，需要实现的业务让ShowModel去继承实现。

（2）编写View层

	package com.xmg.testmvp.View;
	
	/**
	 * Created by xmg on 2016/9/22.
	 */
	
	public interface ShowView {
	
	    void Onshow(String text);
	}

------------------------------------------------------------

	package com.xmg.testmvp.View;
	
	import android.os.Bundle;
	import android.support.v7.app.AppCompatActivity;
	import android.view.View;
	import android.widget.Button;
	import android.widget.TextView;
	
	import com.xmg.testmvp.Presenter.ShowPresenter;
	import com.xmg.testmvp.R;
	
	public class MainActivity extends AppCompatActivity implements ShowView{
	
	    private ShowPresenter presenter;
	    private TextView tv_show;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);

			presenter = new ShowPresenter(this);

	        tv_show = (TextView)findViewById(R.id.tv_show);
	        ((Button)findViewById(R.id.btn_show)).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                presenter.Show();
	            }
	        });
	    }
	
		//显示数据
	    @Override
	    public void Onshow(String text) {
	        tv_show.setText(text);
	    }
	}


在例子中说过Presenter通过接口返回给View（即上述代码中的Activity）,所以定义了ShowView这个接口，Textview需要显示一个数据，所以在接口ShowView中定义个void Onshow(String text)的方法。

（3）编写Presenter

	package com.xmg.testmvp.Presenter;
	
	import com.xmg.testmvp.Model.ShowListerner;
	import com.xmg.testmvp.Model.ShowModel;
	import com.xmg.testmvp.View.MainView;
	
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

根据例子中的要求，我们需要在ShowPresenter编写一个Show()的方法，这个方法就是去调用Mode去加载数据，然后把数据返回给View。

最终效果如下：

![](http://i.imgur.com/uVruNVj.gif)


demo地址：


###总结

感觉上单纯为了简单的实现，而去写这么多的代码，但是逻辑和结构上是更加地清晰了，业务和界面实现的方式完全剥离开。在实际的项目中这种做法更利于后期的开发和维护。




参考资料：

http://blog.csdn.net/yanbober/article/details/45645115



