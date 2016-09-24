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
        tv_show = (TextView)findViewById(R.id.tv_show);
        presenter = new ShowPresenter(this);
        ((Button)findViewById(R.id.btn_show)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.Show();
            }
        });
    }

    @Override
    public void Onshow(String text) {
        tv_show.setText(text);
    }
}
