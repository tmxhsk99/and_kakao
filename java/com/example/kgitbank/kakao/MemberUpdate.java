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
import android.widget.TextView;
import android.widget.Toast;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        final Context ctx = MemberUpdate.this;
        //수정전
        final EditText name =findViewById(R.id.name);
        final EditText phone = findViewById(R.id.phone);
        final EditText addr= findViewById(R.id.addr);
        final EditText email = findViewById(R.id.email);
        final EditText pass = findViewById(R.id.pass);
        final ImageView photo = findViewById(R.id.profile);
        final Intent intent = this.getIntent(); /*데이터 수신*/
        final String arr[] =intent.getStringExtra("spec").split("/");
        // m.seq+"/"+m.addr+"/"+m.email+"/"+m.name+"/"+m.pass+"/"+m.photo+"/"+m.phone;
        email.setHint(arr[2]);
        name.setHint(arr[3]);
        phone.setHint(arr[6]);
        addr.setHint(arr[1]);
        pass.setHint(arr[4]);
         try {

        photo.setImageDrawable(

                getResources().getDrawable(
                        getResources().getIdentifier(
                                this.getPackageName()+":drawable/"+arr[5].toLowerCase(),null,null),ctx.getTheme()
                ));
        }catch (Exception e){
          photo
                    .setImageDrawable(getResources().getDrawable(
                            getResources().getIdentifier(
                                    ctx.getPackageName()+":drawable/" +"err",null,null),ctx.getTheme()
                    ));
        }
        //수정이후부분
        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memeber memeber = new Memeber();
                final itemUpdate query = new itemUpdate(ctx);
                //member에 값을 넣는데 만약 EditText 가 NUll 이라면
                //배열에 있는 값이라도 member에 할당해야 한다.
                memeber.setName(name.getText().toString().equals("") ? arr[3] : name.getText().toString());//null은 주소값이라 안됨

                Log.d("true false 판단",(name.getText().toString().equals("")+""));
                Log.d("안집어넣음",arr[3]);
                memeber.setEmail(email.getText().toString().equals("") ? arr[2] : email.getText().toString());
                memeber.setPhone(phone.getText().toString().equals("") ? arr[6] : phone.getText().toString());
                memeber.setAddr(addr.getText().toString().equals("") ? arr[1] : addr.getText().toString());
                memeber.setPass(pass.getText().toString().equals("") ? arr[4] : pass.getText().toString());
                memeber.setPhoto(arr[5].toLowerCase());
                Log.d("사진의 이름 체크",arr[5].toLowerCase());
                query.memeber = memeber;
                //리턴타입 Void니 받지 않아도 된다.
                 new Main.ExcuteService() {
                    @Override
                    public void perform() {
                        query.excute();
                    }
                }.perform();
                Toast.makeText(ctx,"정보수정완료",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ctx, MemberDetail.class);
                intent.putExtra("seq",String.valueOf(arr[0]));
                startActivity(intent);
            }
        });
        findViewById(R.id.cancleBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MemberDetail.class);
                intent.putExtra("seq",String.valueOf(arr[0]));
                startActivity(intent);
            }
        });
    }//onCreate 끝

    //DB관련 update를 실행한다.
    private class UpdateQuery extends Main.QueryFactory{
        Main.SqliteHelper helper;

        public UpdateQuery(Context ctx) {
            super(ctx);
            helper = new Main.SqliteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }

    private class itemUpdate extends UpdateQuery{
        Memeber memeber;//메모리상
        public itemUpdate(Context ctx) {
            super(ctx);
            memeber = new Memeber();
            //인스턴스변수는 반드시 생성자 내부에서 초기화 한다.
            //로직은 반드시 에어리어 내부에서 이루어진다.
            //에어리어 내부는 CPU를 뜻한다.
            //필드는 RAM 영역을 뜻한다. 그러므로 필드에서 초기화를 하면
            /*C 언어는 데스 리치 1970 ->절차지향
            * PC 는 스티브 워즈니악 1975
            * 자바 제임스 고슬링  1995->객체 지향언어
            * 객체는 속성과 기능의 집합이다
            * 속성은 필드 : 램
            * 1993년 이건희 반도체 개발  1996년 하면서 그래서 만들어짐
            * 기능 :CPU
            * 반드시 필드에서 초기화하면 안된다 .
            * */
        }

        public void excute(){
            String sql = String.format(
                    " UPDATE %s SET "+
                            " %s = '%s' ," +
                            " %s = '%s' ,"+
                            " %s = '%s' ," +
                            " %s = '%s' ," +
                            " %s = '%s' " +
                            " WHERE %s LIKE '%s' ",
                    DBInfo.MBR_TABLE,
                    DBInfo.MBR_ADDR, memeber.addr,
                    DBInfo.MBR_EMAIL, memeber.email,
                    DBInfo.MBR_NAME, memeber.name,
                    DBInfo.MBR_PASS, memeber.pass,
                    DBInfo.MBR_PHOTO,memeber.photo,
                    DBInfo.MBR_PHONE, memeber.phone
                    // m.seq+"/"+m.addr+"/"+m.email+"/"+m.name+"/"+m.pass+"/"+m.photo+"/"+m.phone
            );
            Log.d(" SQL :::", sql);
            getDatabase().execSQL(sql);
        }

    }


}
