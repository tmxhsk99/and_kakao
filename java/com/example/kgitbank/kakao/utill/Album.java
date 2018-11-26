package com.example.kgitbank.kakao.utill;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.kgitbank.kakao.R;

public class Album extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album);
        final Context ctx = Album.this;
        GridView myAlbum = findViewById(R.id.Myalbum);
        String[] arr = {"photo_0","photo_1","photo_2","photo_3","photo_4"};
        myAlbum.setAdapter(new Picture(ctx,arr));

    }

    public class Picture extends BaseAdapter{
        private  Context ctx;
        private  String[] pictures;

        public Picture(Context ctx, String[] pictures) {
            this.ctx = ctx;
            this.pictures = pictures;
        }

        @Override
        public int getCount() {
            return pictures.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View v, ViewGroup p) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
            View gridView;
            if(v==null){
                gridView = new GridView(ctx);
                gridView = inflater.inflate(R.layout.picture,null);
                ImageView iv = gridView.findViewById(R.id.photo);
                String ph = pictures[i];
                switch (ph){
                    case "photo_0" : iv.setImageResource(R.drawable.photo_0);break;
                    case "photo_1" : iv.setImageResource(R.drawable.photo_1);break;
                    case "photo_2" : iv.setImageResource(R.drawable.photo_2);break;
                    case "photo_3" : iv.setImageResource(R.drawable.photo_3);break;
                    case "photo_4" : iv.setImageResource(R.drawable.photo_4);break;
                    default: iv.setImageResource(R.drawable.err);break;
                }
            }else {
                gridView = v;
            }
            return gridView;
        }
    }
}
