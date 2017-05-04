package com.example.administrator.liuliushare.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/4/29.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResultId());
        initView();
        initListener();
        initData();

    }

    public abstract  int getResultId() ;

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData() ;

}
