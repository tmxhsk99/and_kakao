package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        final Context ctx = MemberList.this;
        ListView mbrList = findViewById(R.id.mbrList);

        //Log.d("친구 목록 "."강동원 ");


        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,MemberDetail.class));
            }
        });

    }//OnCreate end

    //데이터 베이스에대한  접근쿼리
    private class ListQuery extends Main.QueryFactory{//DB접근 권한은 제한한다 .
        SQLiteOpenHelper helper;

        public ListQuery(Context ctx){
            super(ctx);
            helper = new Main.SqliteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {return helper.getReadableDatabase(); }//데이터베이스를 관리함
    }//ListQuery end

    private  class ItemList extends ListQuery {
        Memeber memeber;

        public ItemList(Context ctx) {super(ctx);}

        public ArrayList<Member> execute (){
            String s =String.format("SELECT * FROM MEMBER;");

            return null;

        }
    }
    


}
