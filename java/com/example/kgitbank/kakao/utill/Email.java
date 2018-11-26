package com.example.kgitbank.kakao.utill;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Email {
        private Context ctx;

    public Email(Context ctx) {
        this.ctx = ctx;
    }

    public void sendEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"+email));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL,email);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Hello");
        intent.putExtra(Intent.EXTRA_TEXT,"안녕!!!!");
        ctx.startActivity(intent.createChooser(intent,"example"));

    }
}
