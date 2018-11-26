package com.example.kgitbank.kakao.utill;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class Phone {
    private Context ctx;
    private AppCompatActivity activity;
    private String phoneNum;
    public Phone(Context ctx, AppCompatActivity activity) {
        this.ctx = ctx;
        this.activity = activity;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void dial() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));//앞에 문자는 정해진것 android: 같은 것 prefix;
        /*intent ctx 쓸때는 내부 기반
        * 외부적으로 가는것은 ctx를 쓰지 않는다 .*/

    }
    public  void directCall(){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));//앞에 문자는 정해진것 android: 같은 것 prefix;
         if(ActivityCompat.checkSelfPermission(ctx,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CALL_PHONE},2);
                return;
         }
         ctx.startActivity(intent);
    }
    
}
