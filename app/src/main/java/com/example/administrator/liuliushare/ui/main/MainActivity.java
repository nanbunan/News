package com.example.administrator.liuliushare.ui.main;

import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.administrator.liuliushare.R;
import com.example.administrator.liuliushare.ui.base.BaseActivity;
import com.example.administrator.liuliushare.ui.fragment.HomeFragment;
import com.example.administrator.liuliushare.ui.fragment.JiaoYiFragment;
import com.example.administrator.liuliushare.ui.fragment.WodeFragment;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MainActivity extends BaseActivity{
    private static final int TAB_HOME = 0X1;
    private static final int TAB_JIAOYI = 0X2;
    private static final int TAB_WODE = 0X3;
    private static int CURRENT_ITEM = 1;
    private HomeFragment mHomeFragment;
    private JiaoYiFragment mJiaoYiFragment;
    private WodeFragment mWoDeFragment;
    private FrameLayout mFrameLayout;
    private RadioGroup mRadioGroup;


    RadioButton rbTabhome,rbTabjiaoyi,rbTabwode;
    @Override
    public int getResultId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        rbTabjiaoyi= (RadioButton) findViewById(R.id.rbTabjiaoyi);
        rbTabhome= (RadioButton) findViewById(R.id.rbTabHome );
        rbTabwode= (RadioButton) findViewById(R.id.rbTabwode);
        rbTabhome.setChecked(true);
        initFragment();

        addFragment();
        initRadio();

    }

    public void initRadio() {
        mRadioGroup= (RadioGroup) findViewById(R.id.rgTabBar);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbTabHome:
                        changeTab(TAB_HOME);
                        break;
                    case R.id.rbTabwode:
                        changeTab(TAB_WODE);
                        break;
                    case R.id.rbTabjiaoyi:
                        changeTab(TAB_JIAOYI);
                        break;
                }
            }
        });
    }
/*
* 改变tab
* */
    private void changeTab(int checkId) {
        switch(checkId){
            case TAB_HOME:
                CURRENT_ITEM=TAB_HOME;
                showFragment(TAB_HOME);
                rbTabhome.setChecked(true);
                break;
            case TAB_WODE:
                CURRENT_ITEM=TAB_WODE;
                showFragment(TAB_WODE);
                rbTabwode.setChecked(true);
                break;
            case TAB_JIAOYI:
                CURRENT_ITEM=TAB_JIAOYI;
                showFragment(TAB_JIAOYI);
                rbTabjiaoyi.setChecked(true);
                break;
        }
    }

    public void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.container,mHomeFragment);
        transaction.add(R.id.container,mWoDeFragment);
        transaction.add(R.id.container,mJiaoYiFragment);
        transaction.commit();
        showFragment(CURRENT_ITEM);
    }
/*
* 显示当前tab所对应的fragment
* */
    private void showFragment(int tab) {
        hideFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        switch (tab) {
            case TAB_HOME:
                transaction.show(mHomeFragment);
                break;
            case TAB_JIAOYI:
                transaction.show(mJiaoYiFragment);
                break;
            case TAB_WODE:
                transaction.show(mWoDeFragment);
                break;
        }
        transaction.commit();
    }
/*
* 隐藏所有Fragment
* */
    private void hideFragment() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.hide(mHomeFragment);
        transaction.hide(mWoDeFragment);
        transaction.hide(mJiaoYiFragment);
        transaction.commit();
    }

    /*
    * 初始化fragment
    * */
    public void initFragment() {
        mHomeFragment=new HomeFragment();
        mJiaoYiFragment=new JiaoYiFragment();
        mWoDeFragment=new WodeFragment();
    }

    @Override
    public void initListener() {


    }

    @Override
    public void initData() {

    }

    //点击返回弹出窗口
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            new MaterialDialog.Builder(MainActivity.this)
                    .title(getResources().getString(R.string.tip))
                    .content(getResources().getString(R.string.exit))
                    .negativeText(getResources().getString(R.string.cancel))
                    .onNegative((dialog, which) -> dialog.dismiss()).positiveText(getResources().getString(R.string.ok))
                    .onPositive((dialog, which) -> {
                        finish();
                        dialog.dismiss();
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
