package com.example.kgitbank.kakao;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Member;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Context ctx = Login.this;
        final EditText etID = findViewById(R.id.etID);
        final EditText etPass = findViewById(R.id.etPass);
        findViewById(R.id.btLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx,"아이디는"+etID.getText().toString()+"비밀번호는"+etPass.getText().toString()+"입니다",Toast.LENGTH_LONG).show();
                //Validation 유효성 체크
                if (etID.getText().length() != 0 && etPass.getText().length() != 0) {
                     String id = etID.getText().toString();
                     String pw = etPass.getText().toString();
                     Log.d("아이디값",id);
                     Log.d("비밀번호",pw);
                   final itemExist query = new itemExist(ctx);
                    query.id =id;
                    query.pw = pw;
                    new Main.ExcuteService() {
                        @Override
                        public void perform() {
                            if(query.execute()){
                                Log.d("트루반환","아이템 이그지스트 익스큐트");
                                startActivity(new Intent(ctx, MemberList.class));
                            }else {
                                startActivity(new Intent(ctx, Login.class));
                            };
                        }
                    }.perform();


                } else {
                    startActivity(new Intent(ctx, Login.class));

                }

            }
        });
        findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etID.setText("");
                etPass.setText("");
            }
        });

    }//oncreate End

    private class LoginQuery extends Main.QueryFactory {
        Main.SqliteHelper helper;//helper는 DB와 동일시해도 상관없다 .

        public LoginQuery(Context ctx) {
            super(ctx);
            helper = new Main.SqliteHelper(ctx);

        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }//LoginQuey end

    private class itemExist extends LoginQuery {
        String id;
        String pw;

        public itemExist(Context ctx) {
            super(ctx);
        }

        public boolean execute() {
            /*String s =String.format("SELECT * FROM %s " +
                            " WHERE %s LIKE '%s' AND '%s' LIKE '%s'",
                    DBInfo.MBR_TABLE, DBInfo.MBR_SEQ, id,
                    DBInfo.MBR_PASS, pw);*/
            return super
                    .getDatabase()
                    .rawQuery(String.format("SELECT * FROM %s " +
                                    " WHERE %s LIKE '%s' AND '%s' LIKE '%s' ",
                            DBInfo.MBR_TABLE, DBInfo.MBR_SEQ, id,
                            DBInfo.MBR_PASS, pw), null)//null인 이유는 또하나의 설정값이 있기 때문에
                    .moveToNext(); //커서가 이동하면서 체크

        }
    }

}
