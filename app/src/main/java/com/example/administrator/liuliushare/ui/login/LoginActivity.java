package com.example.administrator.liuliushare.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.liuliushare.R;
import com.example.administrator.liuliushare.constant.Constants;
import com.example.administrator.liuliushare.ui.application.BmobApplication;
import com.example.administrator.liuliushare.ui.base.BaseActivity;
import com.example.administrator.liuliushare.ui.main.MainActivity;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;

/**
 * Created by Administrator on 2017/4/29.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    Button send_sms,btn_login,qq_login,wx_login;
    EditText et_phone_number,et_code;
    CheckBox cb_agree;
    @Override
    public int getResultId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        Bmob.initialize(this, Constants.BMOB_APPID);
        BmobSMS.initialize(this, Constants.BMOB_APPID);//b822d7c40b2d3976c8c2b89e9e1a0eaa
        send_sms= (Button) findViewById(R.id.send_sms);
        btn_login= (Button) findViewById(R.id.btn_login);
        et_code= (EditText) findViewById(R.id.et_code);
        et_phone_number= (EditText) findViewById(R.id.et_phone_number);
        qq_login=(Button) findViewById(R.id.btn_qq);
        wx_login= (Button) findViewById(R.id.btn_wx);
        cb_agree= (CheckBox) findViewById(R.id.cb_agree);


    }

    @Override
    public void initListener() {
        send_sms.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        wx_login.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        Log.e("MESSAGE:", "1");
        String phonenumber = et_phone_number.getText().toString();
        String passWord = et_code.getText().toString();
        switch (view.getId()) {
            case R.id.send_sms:
                Log.e("MESSAGE:", "2");
                if (phonenumber.length() != 11) {
                    Toast.makeText(this,R.string.input, Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("MESSAGE:", "3");
                    //进行获取验证码操作和倒计时1分钟操作
                    BmobSMS.requestSMSCode(this, phonenumber, "遛遛享车", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null) {
                                //发送成功时，让获取验证码按钮不可点击，且为灰色
                                send_sms.setClickable(false);
                                send_sms.setBackgroundColor(Color.GRAY);
                                Toast.makeText(LoginActivity.this, R.string.codesuccess, Toast.LENGTH_SHORT).show();
                                /**
                                 * 倒计时1分钟操作
                                 * 说明：
                                 * new CountDownTimer(60000, 1000),第一个参数为倒计时总时间，第二个参数为倒计时的间隔时间
                                 * 单位都为ms，其中必须要实现onTick()和onFinish()两个方法，onTick()方法为当倒计时在进行中时，
                                 * 所做的操作，它的参数millisUntilFinished为距离倒计时结束时的时间，以此题为例，总倒计时长
                                 * 为60000ms,倒计时的间隔时间为1000ms，然后59000ms、58000ms、57000ms...该方法的参数
                                 * millisUntilFinished就等于这些每秒变化的数，然后除以1000，把单位变成秒，显示在textView
                                 * 或Button上，则实现倒计时的效果，onFinish()方法为倒计时结束时要做的操作，此题可以很好的
                                 * 说明该方法的用法，最后要注意的是当new CountDownTimer(60000, 1000)之后，一定要调用start()
                                 * 方法把该倒计时操作启动起来，不调用start()方法的话，是不会进行倒计时操作的
                                 */
                                new CountDownTimer(60000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        //send_sms.setBackgroundResource(R.drawable.button_shape02);
                                        send_sms.setText(millisUntilFinished / 1000 + getString(R.string.second));
                                    }

                                    @Override
                                    public void onFinish() {
                                        send_sms.setClickable(true);
                                        //send_sms.setBackgroundResource(R.drawable.button_shape);
                                        send_sms.setText(R.string.secondsend);
                                    }
                                }.start();
                                Log.e("MESSAGE:", "4");
                            }
                            else {
                                Toast.makeText(LoginActivity.this, R.string.codedefault, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                break;
            case R.id.btn_login:
                Log.e("MESSAGE:", "5");
                if (phonenumber.length() == 0 || et_code.length() == 0 || phonenumber.length() != 11) {
                    Log.e("MESSAGE:", "6");
                    Toast.makeText(this, R.string.notphonenumber, Toast.LENGTH_SHORT).show();
                }else if(!cb_agree.isChecked()){ Toast.makeText(this, R.string.checkagree, Toast.LENGTH_SHORT).show();}
                else {
                    BmobSMS.verifySmsCode(this, phonenumber, passWord, new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.e("MESSAGE:", "7");
                                Toast.makeText(LoginActivity.this,R.string.loginsuccess, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.e("MESSAGE:", "8");
                                Toast.makeText(LoginActivity.this, R.string.isnotcode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.btn_qq:
                qqAuthorize();
                break;
            case R.id.btn_wx:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "bmob_third_login_demo";//可随便写，微信会原样返回
                boolean isOk = BmobApplication.api.sendReq(req);
                Log.i("smile", "是否发送成功："+isOk);
                break;
            default:
                break;
        }

    }
    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo){
        BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
                Log.i("smile",authInfo.getSnsType()+getString(R.string.qqloginsucccess)+userAuth);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("json", userAuth.toString());
                intent.putExtra("from", authInfo.getSnsType());
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                toast(getString(R.string.qqloginloser)+msg);
            }

        });
    }
    public static Tencent mTencent;

    private void qqAuthorize(){
        if(mTencent==null){
            mTencent = Tencent.createInstance(Constants.QQ_APP_ID, getApplicationContext());//QQ_APP_ID="1105704769"
        }
        mTencent.logout(this);
        mTencent.login(this, "all", new IUiListener() {

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if(arg0!=null){
                    JSONObject jsonObject = (JSONObject) arg0;
                    try {
                        String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                        String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                        String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ,token, expires,openId);
                        loginWithAuth(authInfo);
                    } catch (JSONException e) {
                    }
                }
            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                toast(getString(R.string.qqacceptloser)+arg0.errorCode+"--"+arg0.errorDetail);
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                toast(getString(R.string.cancelqqaccept));
            }
        });
    }

    private void toast(String msg){
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
