package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.example.kgitbank.kakao.Memeber;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        final Context ctx = MemberList.this;
        ListView mbrList = findViewById(R.id.mbrList);
        final ItemList query = new ItemList(ctx);
        /*List<Memeber> s = (List<Memeber>) new Main.ListService() {
            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform();*/
        MemberAdapter memberAdapter = new MemberAdapter((ArrayList<Memeber>) new Main.ListService() {
            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform());

       // Log.d("친구 목록", s.toString());


        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, MemberDetail.class));
            }
        });

    }//OnCreate end

    //데이터 베이스에 대한  접근쿼리
    private class ListQuery extends Main.QueryFactory {//DB접근 권한은 제한한다 .
        SQLiteOpenHelper helper;

        public ListQuery(Context ctx) {
            super(ctx);
            helper = new Main.SqliteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }//데이터베이스를 관리함
    }//ListQuery end

    private class ItemList extends ListQuery {

        public ItemList(Context ctx) {
            super(ctx);
        }

        public ArrayList<Memeber> execute() {
            ArrayList<Memeber> ls = new ArrayList<>();
            Cursor c = this.getDatabase().rawQuery(
                    "SELECT * FROM MEMBER", null
            );
            Memeber m = null;
            if (c != null) {
                while (c.moveToNext()) {
                    m= new Memeber();
                    m.setSeq(Integer.parseInt(c.getString(c.getColumnIndex(DBInfo.MBR_SEQ))));
                    m.setPass(c.getString(c.getColumnIndex(DBInfo.MBR_PASS)));
                    m.setName(c.getString(c.getColumnIndex(DBInfo.MBR_NAME)));
                    m.setAddr(c.getString(c.getColumnIndex(DBInfo.MBR_ADDR)));
                    m.setEmail(c.getString(c.getColumnIndex(DBInfo.MBR_EMAIL)));
                    m.setPhone(c.getString(c.getColumnIndex(DBInfo.MBR_PHONE)));
                    m.setPhoto(c.getString(c.getColumnIndex(DBInfo.MBR_PHOTO)));
                    ls.add(m);
                }

            }else{
                Log.d("등록된 회원은 ","없습니다.");
            }
            return ls;

        }
    }
    /*아이템관련 파트 */
    private  class MemberAdapter extends BaseAdapter{
        ArrayList<Memeber> ls;
        public MemberAdapter(ArrayList<Memeber> ls) {
            this.ls = ls;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
