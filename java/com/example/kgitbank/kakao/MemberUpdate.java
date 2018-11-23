package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        final Context ctx = MemberUpdate.this;
        final Intent intent = this.getIntent(); /*데이터 수신*/
        String spec = intent.getExtras().getString("spec"); /*String형*/
        Log.d("받은 것 ",spec);
        String arr[] = spec.split("/");
        EditText name =findViewById(R.id.name);
        name.setHint(arr[3]);
        EditText email = findViewById(R.id.email);
        email.setHint(arr[2]);
        EditText phone = findViewById(R.id.phone);
        phone.setHint(arr[6]);
        EditText addr= findViewById(R.id.addr);
        addr.setHint(arr[1]);


        findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,MemberDetail.class));
            }
        });

    }
}
