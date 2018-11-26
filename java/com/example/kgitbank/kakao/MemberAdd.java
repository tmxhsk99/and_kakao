package com.example.kgitbank.kakao;

        import android.content.Context;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.lang.reflect.Member;

public class MemberAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_add);
        final Context ctx =  MemberAdd.this;
        final ImageView photo = findViewById(R.id.profile);
        final EditText photoName = findViewById(R.id.profileName);
        final EditText name = findViewById(R.id.name);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText addr = findViewById(R.id.addr);


        findViewById(R.id.imgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().length()!=0 && email.getText().length()!=0 && phone.getText().length()!=0 && addr.getText().length()!=0){
                         Memeber m = new Memeber();
                         m.setName(name.getText().toString());
                         m.setEmail(email.getText().toString());
                         m.setPhone(phone.getText().toString());
                         m.setAddr(addr.getText().toString());
                        final ItemAdd query = new ItemAdd(ctx,m);
                        new Main.ExcuteService(){

                            @Override
                            public void perform() {
                                     query.execute();
                            }
                        }.perform();
                         Toast.makeText(ctx,"등록 완료",Toast.LENGTH_LONG).show();
                          startActivity(new Intent(ctx, MemberList.class));     

                }else{
                     Toast.makeText(ctx,"입력하지 않은 항목이 존재합니다",Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.listBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(ctx, MemberList.class));
            }
        });


    }//onCreate end

    //멤버 추가 DB 쿼리
    private class AddQuery extends Main.QueryFactory{
        Main.SqliteHelper helper;

        public AddQuery(Context ctx) {
            super(ctx);
            this.helper = new Main.SqliteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }

    private class ItemAdd extends  AddQuery{
        Memeber m;

        public ItemAdd(Context ctx, Memeber memeber) {
            super(ctx);
            this.m= memeber;
        }
        public void execute(){
            String sql = String.format(" INSERT INTO %s ( %s, %s, %s ,%s ,%s, %s)" +
                                        "VALUES( '%s' ,'%s', '%s' , '%s', '%s','%s' )",
                                         DBInfo.MBR_TABLE, DBInfo.MBR_NAME, DBInfo.MBR_EMAIL, DBInfo.MBR_PASS,
                                        DBInfo.MBR_ADDR, DBInfo.MBR_PHONE, DBInfo.MBR_PHOTO,
                                        m.name,m.email,'1' ,m.addr,m.phone,"err");
            Log.d("Itemadd 실행된 SQL문 :::::::",sql);
                super.getDatabase().execSQL(sql);

        }
    }
}
