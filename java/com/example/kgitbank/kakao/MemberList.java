package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        final ListView mbrList = findViewById(R.id.mbrList);
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

        //Detail 처리하는 부분
        mbrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int i, long l) {
                Memeber m = (Memeber) mbrList.getItemAtPosition(i);
                Log.d("선택한 ID",m.seq+"");
                Intent intent = new Intent(ctx, MemberDetail.class);
                intent.putExtra("seq",String.valueOf(m.seq));
                startActivity(intent);

            }
        });
        //삭제 처리  길게 누른거
        mbrList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> p, View v, int i, long l) {
                final Memeber m  = (Memeber) mbrList.getItemAtPosition(i);
                new AlertDialog.Builder(ctx)
                        .setTitle("삭 제")
                        .setMessage("정말 삭제 하시겠습니까?")
                        .setPositiveButton(
                                (android.R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //삭제 쿼리
                                       final ItemDelete query = new ItemDelete(ctx,String.valueOf(m.seq));
                                            Log.d("전달 받은 seq의 값", m.seq+"");
                                        new Main.ExcuteService(){
                                            @Override
                                            public void perform() {
                                                query.execute();
                                            }
                                        }.perform();
                                        Toast.makeText(ctx,"삭제완료",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(ctx,MemberList.class));
                                    }
                                }
                        ).setNegativeButton(
                        android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ctx,"삭제취소",Toast.LENGTH_LONG).show();

                            }
                        }
                ).show();

                return true;//return 트루여야 반응을한다.
            }
        });
        //추가
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,MemberAdd.class));
            }
        });
    }//OnCreate end



    //데이터 베이스에 대한  접근쿼리
    //삭제 쿼리
    private class DeleteQuery extends  Main.QueryFactory{
        SQLiteOpenHelper helper;
        public DeleteQuery(Context ctx) {
            super(ctx);
            helper = new Main.SqliteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }

    }
    private  class ItemDelete extends DeleteQuery{
        String seq;


        public ItemDelete(Context ctx, String seq) {
            super(ctx);
            this.seq = seq;
        }
        public void execute(){
            String sql = String.format(" DELETE FROM %s " +
                                        " WHERE %s LIKE '%s' "
                                        ,DBInfo.MBR_TABLE,DBInfo.MBR_SEQ,seq
                                        );           //DELETE중
            super.getDatabase().execSQL(sql);
        }

    }


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
                holder.photo = v.findViewById(R.id.photo);
                holder.name = v.findViewById(R.id.name);
                holder.phone = v.findViewById(R.id.phone);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
            holder.name.setText(ls.get(i).getName());
            holder.phone.setText(ls.get(i).getPhone());
            //포토 불러오는 코드
            final ItemPhoto query = new ItemPhoto(ctx);
            query.seq = ls.get(i).seq+"";
            String s = ((String)new Main.OjectService() {
                @Override
                public Object perform() {
                    return query.execute();
                }
            }.perform()).toLowerCase();
            Log.d("파일명 : ",s);
            try {


            holder.photo
                    .setImageDrawable(getResources().getDrawable(
                            getResources().getIdentifier(
                                    ctx.getPackageName()+":drawable/" +s,null,null),ctx.getTheme()
                            ));
            }catch (Exception e){
                holder.photo
                        .setImageDrawable(getResources().getDrawable(
                                getResources().getIdentifier(
                                        ctx.getPackageName()+":drawable/" +"err",null,null),ctx.getTheme()
                        ));
            }
            return v;
        }
    }

    static class ViewHolder {
        ImageView photo;
        TextView name, phone,email,addr;
    }


   private class PhotoQuery extends Main.QueryFactory{
       Main.SqliteHelper helper;
       public PhotoQuery(Context ctx) {
           super(ctx);
           helper =new Main.SqliteHelper(ctx);
       }

       @Override
       public SQLiteDatabase getDatabase() {
           return helper.getReadableDatabase();
       }


   }
    private class ItemPhoto extends PhotoQuery{
        String seq;
        public ItemPhoto(Context ctx) {
            super(ctx);
        }
        public String execute(){
            Cursor c= getDatabase()
                    .rawQuery(String.format(
                            " SELECT %s FROM %s WHERE %s LIKE '%s' ",
                            DBInfo.MBR_PHOTO,
                            DBInfo.MBR_TABLE,
                            DBInfo.MBR_SEQ,
                            seq
                    ),null);
            String result = "";
            if(c!= null){
                if(c.moveToNext()){
                    result = c.getString(c.getColumnIndex(DBInfo.MBR_PHOTO));
                }
            }
            return result;
        }
    }



}
