package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

        mbrList.setAdapter(new MemberAdapter(ctx, (ArrayList<Memeber>) new Main.ListService() {
            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform()));

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
            Log.d("excute", "1");
            ArrayList<Memeber> ls = new ArrayList<>();
            Cursor c = this.getDatabase().rawQuery(
                    "SELECT * FROM MEMBER", null
            );
            Memeber m = null;
            if (c != null) {
                Log.d("excute", "2");
                while (c.moveToNext()) {
                    m = new Memeber();
                    m.setSeq(Integer.parseInt(c.getString(c.getColumnIndex(DBInfo.MBR_SEQ))));
                    m.setPass(c.getString(c.getColumnIndex(DBInfo.MBR_PASS)));
                    m.setName(c.getString(c.getColumnIndex(DBInfo.MBR_NAME)));
                    m.setAddr(c.getString(c.getColumnIndex(DBInfo.MBR_ADDR)));
                    m.setEmail(c.getString(c.getColumnIndex(DBInfo.MBR_EMAIL)));
                    m.setPhone(c.getString(c.getColumnIndex(DBInfo.MBR_PHONE)));
                    m.setPhoto(c.getString(c.getColumnIndex(DBInfo.MBR_PHOTO)));
                    ls.add(m);
                }

            } else {
                Log.d("등록된 회원은 ", "없습니다.");
            }
            Log.d("excute", "3");
            return ls;

        }
    }

    /*아이템관련 파트 */
    private class MemberAdapter extends BaseAdapter {
        ArrayList<Memeber> ls;
        Context ctx;
        LayoutInflater inflater;//글자 새긴 풍선

        public MemberAdapter(Context ctx, ArrayList<Memeber> ls) {
            this.ls = ls;
            this.ctx = ctx;
            this.inflater = LayoutInflater.from(ctx);
        }

        @Override
        public int getCount() {
            return ls.size();
        }

        @Override
        public Object getItem(int i) {
            return ls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if (v == null) {
                v = inflater.inflate(R.layout.mbr_item, null);
                holder = new ViewHolder();
                //holder.photo = v.findViewById(R.id.photo);
                holder.name = v.findViewById(R.id.name);
                holder.phone = v.findViewById(R.id.phone);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
            holder.name.setText(ls.get(i).getName());
            holder.phone.setText(ls.get(i).getPhone());
            //포토 불러오는 코드

            return v;
        }
    }

    static class ViewHolder {
        ImageView photo;
        TextView name, phone;
    }

    private class PhotoQuery extends Main.QueryFactory {
        SQLiteOpenHelper helper;

        public PhotoQuery(Context ctx) {
            super(ctx);
            helper = new Main.SqliteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }

    private class ItemPhoto extends PhotoQuery {

        public ItemPhoto(Context ctx) {
            super(ctx);
        }

        public ArrayList<String> execute() {
            ArrayList<String> ls = new ArrayList<>();
            Cursor c = this.getDatabase().rawQuery("SELECT MBR_SEQ FROM MEMBER ", null);
            String s ;
            while (c.moveToNext()){
                //s=c.getString(c.getColumnIndex().DBInfo.MBR_SEQ);

            }
            return  null;
        }
    }


}
