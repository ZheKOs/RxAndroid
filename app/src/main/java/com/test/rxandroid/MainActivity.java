package com.test.rxandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_wnetwork);
        setTitle(R.string.work_with_network);

        textView = (TextView) findViewById(R.id.tv_network_res);

        MainExample mainExample = new MainExample();

        mainExample.example4(textView,"http://www.pcz.pl/");

    }


}
