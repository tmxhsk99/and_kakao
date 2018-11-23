package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        final Context ctx = MemberDetail.this;
        //선택한 멤버 정보를 로그로 출력하기
        final Intent intent = this.getIntent(); /*데이터 수신*/
        String seq = intent.getExtras().getString("seq"); /*String형*/
        final ItemDetail query = new ItemDetail(ctx);
        query.seq = seq;
       Memeber m = (Memeber) new Main.OjectService(){

            @Override
            public Object perform() {
                return query.execute();
            }
        }.perform();

       Log.d("선택한 맴버정보 ",m.toString());
        new Main.OjectService(){
            @Override
            public Object perform() {
                return null;
            }
        }.perform();
        TextView name=findViewById(R.id.name);
        name.setText(m.getName());
        TextView email=findViewById(R.id.email);
        email.setText(m.getEmail());
        TextView phone=findViewById(R.id.phone);
        phone.setText(m.getPhone());
        TextView addr=findViewById(R.id.addr);
        addr.setText(m.getAddr());
        ImageView photo = findViewById(R.id.profile);
        Log.d("포토이름 -----------------------------------",m.photo);
        photo.setImageDrawable(getResources().getDrawable(
                        getResources().getIdentifier(
                                ctx.getPackageName()+":drawable/" +m.photo.toLowerCase(),null,null),ctx.getTheme()
                ));



        final String spec = m.seq+"/"+m.addr+"/"+m.email+"/"+m.name+"/"+m.pass+"/"+m.photo+"/"+m.phone;
        findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,MemberUpdate.class);
                intent.putExtra("spec", spec);
                startActivity(new Intent(intent));
            }
        });
    }


    /*데이터 베이스 영역 */
    private class DetailQuery extends  Main.QueryFactory{
        Main.SqliteHelper helper;//데이터베이스를 끌고옴

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
            String seq;

            public ItemDetail(Context ctx) {
                super(ctx);
            }

            public Memeber execute(){
                Memeber m = null;
                String s = String.format(" SELECT * FROM %s " +
                                " WHERE %s LIKE '%s' ",
                        DBInfo.MBR_TABLE, DBInfo.MBR_SEQ, seq
                        );

                Cursor c = this.getDatabase().rawQuery(s,null);
                if(c!=null){
                    if (c.moveToNext()) {
                        m =  new Memeber();
                        m.setSeq(Integer.parseInt(c.getString(c.getColumnIndex(DBInfo.MBR_SEQ))));
                        m.setPass(c.getString(c.getColumnIndex(DBInfo.MBR_PASS)));
                        m.setName(c.getString(c.getColumnIndex(DBInfo.MBR_NAME)));
                        m.setAddr(c.getString(c.getColumnIndex(DBInfo.MBR_ADDR)));
                        m.setEmail(c.getString(c.getColumnIndex(DBInfo.MBR_EMAIL)));
                        m.setPhone(c.getString(c.getColumnIndex(DBInfo.MBR_PHONE)));
                        m.setPhoto(c.getString(c.getColumnIndex(DBInfo.MBR_PHOTO)));
                        Log.d("검색된 회원은 ", m.getName());
                    } else {
                        Log.d("검색된 회원이 ", "없습니다.");
                    }
                }
                return m;
            }

    }


}
