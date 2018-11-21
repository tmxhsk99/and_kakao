package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        final Context ctx = MemberDetail.this;
        final ItemDetail query = new ItemDetail(ctx);

        new Main.OjectService(){
            @Override
            public Object perform() {
                return null;
            }
        }.perform();

        findViewById(R.id.moveUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,MemberUpdate.class));
            }
        });
        findViewById(R.id.moveList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,MemberList.class));
            }
        });
    }

    private class DetailQuery extends  Main.QueryFactory{
        Main.SqliteHelper helper;

        public DetailQuery(Context ctx) {
            super(ctx);
            helper = new Main.SqliteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }

    private class ItemDetail extends DetailQuery{

            public ItemDetail(Context ctx) {
                super(ctx);
            }

            public Memeber execute(){
                return  null;
            }

    }


}
